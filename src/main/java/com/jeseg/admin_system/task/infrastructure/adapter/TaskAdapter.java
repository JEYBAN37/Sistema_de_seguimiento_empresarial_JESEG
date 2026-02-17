package com.jeseg.admin_system.task.infrastructure.adapter;

import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import com.jeseg.admin_system.company.infrastructure.repository.CompanyRepository;
import com.jeseg.admin_system.hierarchyNode.domain.dto.NodeResponse;
import com.jeseg.admin_system.hierarchyNode.infrastructure.entity.HierarchyNodeEntity;
import com.jeseg.admin_system.hierarchyNode.infrastructure.repository.HierarchyNodeRepository;
import com.jeseg.admin_system.task.domain.dto.*;
import com.jeseg.admin_system.task.domain.intreface.TaskInterface;


import com.jeseg.admin_system.task.infrastructure.entity.*;
import com.jeseg.admin_system.task.infrastructure.repository.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class TaskAdapter implements TaskInterface {

    private final CompanyRepository companyRepository;
    private final TaskRepository taskRepository;
    private final HierarchyNodeRepository hierarchyNodeRepository;
    private final TaskAssignmentRepository taskAssignmentRepository;
    private final TaskApprovalRepository taskApprovalRepository;
    private final TaskScheduleRepository taskScheduleRepository;
    private final TaskCommentRepository taskCommentRepository;


    @Override
    @Transactional // Si falla el guardado de un anexo, se borra la tarea y las asignaciones automáticamente
    public TaskResponse saveTask(TaskCreateRequest request) {

        List<Long> idsLongNodes =  request.getAssignedNodes();

        List<Long> idsLongApprovals = request.getApprovalRequired();

        // 1. Obtener dependencias principales
        CompanyEntity company = companyRepository.findById(request.getCompany())
                .orElseThrow(BusinessException.Type.ERROR_GUARDAR_HIERARCHY_COMPANIA_NO_EXISTE::build);

        HierarchyNodeEntity creator = hierarchyNodeRepository.findById(request.getCreatedBy())
                .orElseThrow(BusinessException.Type.ERROR_GUARDAR_TAREA_NODO_NO_EXISTE::build);

        // 2. Persistir Tarea Principal
        TaskEntity task = persistMainTask(request, company, creator);


        // 3. Si existe novedad agrega novedad a la tabla de novedades
        if (request.getComment() != null && !request.getComment().isEmpty()) {
            persistantComment(request.getComment(), task, creator);
        }


        // 3. Persistir Programación (Si aplica)
        //if (request.getRecurrenceType() != null) {
            //saveTaskSchedule(request, task);
        //}

        // 4. Persistir Asignaciones
        if (request.getAssignedNodes() != null && !request.getAssignedNodes().isEmpty()) {
            saveAssignments(idsLongNodes, task);
        }

        // 5. Persistir Aprobadores
        if (request.getApprovalRequired() != null) {
            saveApprovals(idsLongApprovals, task);
        }

        return TaskResponse.builder()
                .id(task.getId())
                .build();
    }

    // --- MÉTODOS PRIVADOS DE SOPORTE ---
    private void persistantComment(String novedad, TaskEntity task, HierarchyNodeEntity creator) {

        TaskCommentEntity comment = TaskCommentEntity.builder()
                .task(task)
                .comment(novedad)
                .createdAt(LocalDateTime.now())
                .createdBy(creator)
                .build();

        taskCommentRepository.save(comment);
    }

    private TaskEntity persistMainTask(TaskCreateRequest request, CompanyEntity company, HierarchyNodeEntity creator) {
        return taskRepository.save(TaskEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status("CREATED")
                .approvalRequired(request.getApprovalRequired() != null)
                .company(company)
                .createdBy(creator)
                .createdAt(convertTime(request.getCreatedAt()))
                .startDate(convertTime(request.getStart()))
                .endDate(convertTime(request.getEnd()))
                .placeOrLocation(request.getUbicacion())
                .priority(TaskPriority.fromCode(request.getPriority()))
                .build());
    }

    private LocalDateTime convertTime(List<Integer> a) {
        if (a == null || a.size() < 3) return null;

        int year = a.get(0);
        int month = a.get(1);
        int day = a.get(2);
        int hour = a.size() > 3 ? a.get(3) : 0;
        int minute = a.size() > 4 ? a.get(4) : 0;

        LocalDateTime ldt = LocalDateTime.of(year, month, day, hour, minute);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("America/Bogota"));

        return zdt.toLocalDateTime();
    }

    private void saveTaskSchedule(TaskCreateRequest request, TaskEntity task) {
        TaskScheduleEntity schedule = TaskScheduleEntity.builder()
                .task(task)
                .recurrenceType(RecurrenceType.fromCode(request.getRecurrenceType()))
                //.startDate(request.getStart())
                //.endDate(request.getEnd())
                .build();
        taskScheduleRepository.save(schedule);
    }

    private void saveAssignments(List<Long> nodeIds, TaskEntity task) {
        List<HierarchyNodeEntity> nodes = hierarchyNodeRepository.findAllById(nodeIds);
        List<TaskAssignmentEntity> assignments = nodes.stream()
                .map(node -> TaskAssignmentEntity.builder()
                        .task(task)
                        .empleado(node)
                        .build())
                .toList();
        taskAssignmentRepository.saveAll(assignments);
    }

    private void saveApprovals(List<Long> approverIds, TaskEntity task) {
        List<HierarchyNodeEntity> approvers = hierarchyNodeRepository.findAllById(approverIds);
        List<TaskApprovalEntity> approvals = approvers.stream()
                .map(node -> TaskApprovalEntity.builder()
                        .task(task)
                        .approver(node)
                        .status(ApprovalStatus.PENDING)
                        .approvalOrder(1)
                        .build())
                .toList();
        taskApprovalRepository.saveAll(approvals);
    }


    @Override
    public List<TaskCheckResponse> getAllTasksByHerarchyId(TaskFiltersRequest filters) {
        return getAllTasks(taskRepository.findAll((root, query, cb) -> {
            query.distinct(true);
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por Título (Parcial: LIKE)
            if (filters.getTitle() != null && !filters.getTitle().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + filters.getTitle().toLowerCase() + "%"));
            }

            // Filtro por Estado (Exacto)
            if (filters.getStatus() != null && !filters.getStatus().isEmpty()) {
                predicates.add(cb.equal(root.get("status"), filters.getStatus()));
            }

            // Filtro por Prioridad (Usando tu Enum)
            if (filters.getPriority() != null) {
                predicates.add(cb.equal(root.get("priority"), TaskPriority.valueOf(filters.getPriority())));
            }

            // Filtro por Fecha de Creación
            if (filters.getCreateAt() != null) {
                LocalDate date = LocalDate.parse(filters.getCreateAt());
                predicates.add(cb.equal(root.get("createdAt"), date));
            }

            // Filtro entre Fecha de Inicio
            if (filters.getStartDate() != null && filters.getEndDate() != null) {
                LocalDate startDateFrom = LocalDate.parse(filters.getStartDate());
                LocalDate startDateTo = LocalDate.parse(filters.getEndDate());
                predicates.add(cb.between(root.get("startDate"), startDateFrom, startDateTo));
            }

            // Filtro por Creador (Relación ManyToOne)
            if (filters.getHerarchyId() != null) {
                predicates.add(cb.equal(root.get("createdBy").get("id"), filters.getHerarchyId()));
            }

            // Filtro por Creador (Relación ManyToOne)
            if (filters.getAssignedUserId() != null) {
                predicates.add(cb.equal(root.get("createdBy").get("id"), filters.getAssignedUserId()));
            }


            if (filters.getRecurrence() != null && filters.getRecurrence()) {
                // Esto hace un JOIN con la colección de horarios (usa el nombre de la propiedad Java)
                root.join("taskSchedules", JoinType.INNER);
            }

            if (filters.getRecurrence() == null) {
                // Esto hace un LEFT JOIN con la colección de horarios y filtra las tareas sin horarios asociados
                Join<TaskEntity, TaskScheduleEntity> scheduleJoin = root.join("taskSchedules", JoinType.LEFT);
                predicates.add(cb.isNull(scheduleJoin.get("id")));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }));
    }


    public List<TaskCheckResponse> getAllTasks(List<TaskEntity> tasks) {


        // 1. Validar si la lista viene nula o vacía
        if (tasks == null || tasks.isEmpty()) {
            return new ArrayList<>();
        }

        return tasks.stream()
                .map(t -> TaskCheckResponse.builder()
                        .id(t.getId())
                        .title(t.getTitle())
                        .description(t.getDescription())
                        .ubicacion(t.getPlaceOrLocation())
                        .comments(t.getTaskSchedules() != null && !t.getTaskSchedules().isEmpty() ? mapToCommentResponse(t.getComments()) : null)
                        .status(t.getStatus())
                        .approvalRequired(mapToNodeResponse(t.getApprovals()))
                        .assignedNodes(mapToNodeResponseAssignments(t.getAssignments()))
                        //.createdAt(t.getCreatedAt())
                        //.priority(t.getPriority() != null ? t.getPriority().name() : null)
                        //.startDate(t.getStartDate())
                        //.endDate(t.getEndDate())
                        .build())
                .toList();
    }

    private List<CommendResponse> mapToCommentResponse(Set<TaskCommentEntity> comments) {
        if (comments == null) return null;

        return comments.stream()
                .map(c -> CommendResponse.builder()
                        .comment(c.getComment())
                        //.createdAt(c.getCreatedAt())
                        .createdBy(c.getCreatedBy().getName())
                        .build())
                .toList();
    }



    /**
     * Convierte un HierarchyNodeEntity al DTO NodeResponse
     */
    private List<NodeResponse> mapToNodeResponse(Set<TaskApprovalEntity> nodes) {
        if (nodes == null) return null;

        return nodes.stream()
                .map(n -> NodeResponse.builder()
                        .id(n.getApprover().getId())
                        .name(n.getApprover().getName())
                        .idUser(n.getApprover().getUsers() != null && !n.getApprover().getUsers().isEmpty()
                                ? n.getApprover().getUsers().get(0).getId()
                                : null)
                        .nombreCompleto(n.getApprover().getUsers() != null && !n.getApprover().getUsers().isEmpty()
                                ? n.getApprover().getUsers().get(0).getNombreCompleto()
                                : null)
                        .build())
                .toList();
    }

    private List<NodeResponse> mapToNodeResponseAssignments(Set<TaskAssignmentEntity> nodes) {
        if (nodes == null) return null;

        return nodes.stream()
                .map(n -> NodeResponse.builder()
                        .id(n.getEmpleado().getId())
                        .name(n.getEmpleado().getName())
                        .idUser(n.getEmpleado().getUsers() != null && !n.getEmpleado().getUsers().isEmpty()
                                ? n.getEmpleado().getUsers().get(0).getId()
                                : null)
                        .nombreCompleto(n.getEmpleado().getUsers() != null && !n.getEmpleado().getUsers().isEmpty()
                                ? n.getEmpleado().getUsers().get(0).getNombreCompleto()
                                : null)
                        .roleName(n.getEmpleado().getUsers() != null  && !n.getEmpleado().getUsers().isEmpty() ? n.getEmpleado().getUsers().get(0).getRole().getName() : null)
                        .celular(n.getEmpleado().getUsers() != null  && !n.getEmpleado().getUsers().isEmpty() ? n.getEmpleado().getUsers().get(0).getTelefono() : null)
                        .build())
                .toList();
    }
}