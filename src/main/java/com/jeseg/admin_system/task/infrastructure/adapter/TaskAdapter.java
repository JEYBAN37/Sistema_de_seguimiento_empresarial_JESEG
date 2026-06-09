package com.jeseg.admin_system.task.infrastructure.adapter;

import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.hierarchyNode.infrastructure.entity.HierarchyNodeEntity;
import com.jeseg.admin_system.parameters.domain.dto.Responsable;
import com.jeseg.admin_system.parameters.infrastructure.entity.ResponsableEntity;
import com.jeseg.admin_system.parameters.infrastructure.entity.UbicacionesEntity;
import com.jeseg.admin_system.parameters.infrastructure.repository.ResponsableRepository;
import com.jeseg.admin_system.parameters.infrastructure.repository.UbicacionRepository;
import com.jeseg.admin_system.task.domain.dto.*;
import com.jeseg.admin_system.task.domain.intreface.TaskInterface;


import com.jeseg.admin_system.task.infrastructure.entity.*;
import com.jeseg.admin_system.task.infrastructure.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static com.jeseg.admin_system.task.domain.model.Convert.convertTime;
import static com.jeseg.admin_system.task.domain.model.Convert.convertTimeToList;

@Repository
@RequiredArgsConstructor
public class TaskAdapter implements TaskInterface {


    @Value("${app.tareas-control.hora-limite-tarea}")
    private Integer horaLimiteTarea;

    @Value("${app.tareas-control.hora-limite-finalizacion}")
    private Integer horaLimiteFinalizacion;
    private final ResponsableRepository responsableRepository;
    private final TaskRepository taskRepository;
    private final TaskCommentRepository taskCommentRepository;
    private final UbicacionRepository ubicacionRepository;


    @Override
    @Transactional("primaryTransactionManager")
    public TaskResponse saveTask(TaskCreateRequest request) {

        // 1. Validaciones Previas
        validacionesPrevias(request);

        // 2. Persistir Tarea Principal
        TaskEntity task = persistMainTask(request);


        return TaskResponse.builder()
                .id(task.getId())
                .build();
    }


    private TaskEntity persistMainTask(TaskCreateRequest request) {

        TaskEntity task = TaskEntity.builder()
                .title(request.getTitulo() != null ? String.join(",", request.getTitulo()) : null)
                .startDate(convertTime(request.getFechaInicio()))
                .endDate(convertTime(request.getFechaFin()))
                .equipo(request.getEquipo())
                .description(request.getDescripcion())
                .novedad(request.getNovedad())
                .perfiles(request.getPerfiles() != null ? String.join(",",request.getPerfiles()) : null)
                .ubicacion(request.getUbicacion())
                .responsable(request.getResponsable() != null ? request.getResponsable().getId() : null)
                .territorio(request.getTerritorio())
                .db(request.getDb())
                .transporte(request.getTransporte())
                .transporteDetalles(request.getTransporteDetalles())
                .lugarSalida(request.getLugarSalida())
                .tipoTransporte(request.getTipoTransporte())
                .numPasajeros(request.getNumPasajeros())
                .lugarLlegada(request.getLugarLlegada())
                .status(request.getEstado())
                .transporteAprobado(request.getTransporte() != null && request.getTransporte().equals("SI") ? "PENDIENTE APROVACION" : "NO_APLICA")
                .createdAt(LocalDateTime.now())
                .build();

        if (request.getId() != null) {
            Optional<TaskEntity> taskUpdate = taskRepository.findById(request.getId());
            if (taskUpdate.isEmpty()) {
                throw BusinessException.Type.ERROR_GUARDAR_TAREA_NO_EXISTE.build();
            }

            TaskEntity existingTask = getTask(taskUpdate, task);
            return taskRepository.save(existingTask);
        }

        return taskRepository.save(task);
    }


    private static TaskEntity getTask(Optional<TaskEntity> taskUpdate, TaskEntity task) {
        TaskEntity existingTask = taskUpdate.get();

        // Solo actualiza si el nuevo dato NO es nulo (y opcionalmente si no está vacío)
        if (task.getTitle() != null) existingTask.setTitle(task.getTitle());
        if (task.getStartDate() != null) existingTask.setStartDate(task.getStartDate());
        if (task.getEndDate() != null) existingTask.setEndDate(task.getEndDate());
        if (task.getEquipo() != null) existingTask.setEquipo(task.getEquipo());
        if (task.getDescription() != null) existingTask.setDescription(task.getDescription());
        if (task.getNovedad() != null) existingTask.setNovedad(task.getNovedad());
        if (task.getPerfiles() != null) existingTask.setPerfiles(task.getPerfiles());
        if (task.getUbicacion() != null) existingTask.setUbicacion(task.getUbicacion());
        if (task.getResponsable() != null) existingTask.setResponsable(task.getResponsable());
        if (task.getTerritorio() != null) existingTask.setTerritorio(task.getTerritorio());
        if (task.getDb() != null) existingTask.setDb(task.getDb());

        // Validación para transporte y detalles
        if (task.getTransporte() != null) existingTask.setTransporte(task.getTransporte());
        if (task.getTransporteDetalles() != null) existingTask.setTransporteDetalles(task.getTransporteDetalles());
        if (task.getLugarSalida() != null) existingTask.setLugarSalida(task.getLugarSalida());
        if (task.getTipoTransporte() != null) existingTask.setTipoTransporte(task.getTipoTransporte());
        if (task.getNumPasajeros() != null) existingTask.setNumPasajeros(task.getNumPasajeros());
        if (task.getLugarLlegada() != null) existingTask.setLugarLlegada(task.getLugarLlegada());
        if (task.getStatus() != null) existingTask.setStatus(task.getStatus());

        // El estado del transporte solo cambia si se envió una actualización del campo transporte
        if (task.getTransporteAprobado() != null && !task.getTransporteAprobado().equals("NO_APLICA")) {
            existingTask.setTransporteAprobado(task.getTransporteAprobado());
        }

        existingTask.setUpdatedAt(LocalDateTime.now());
        return existingTask;
    }


    private void validacionesPrevias( TaskCreateRequest request) {

        LocalDateTime fechaInicio = convertTime(request.getFechaInicio());
        LocalDateTime fechaFin = convertTime(request.getFechaFin());

        if (request.getFechaInicio() != null && request.getFechaFin() != null) {

            if (fechaInicio.isAfter(fechaFin)) {
                throw BusinessException.Type.ERROR_GUARDAR_TAREA_FECHA_INVALIDA.build();
            }

            if (!fechaInicio.toLocalDate().equals(fechaFin.toLocalDate())) {
                throw BusinessException.Type.ERROR_GUARDAR_TAREA_FECHA_DIA_DIFERENTE.build();
            }

            // no puede inicar antes de las 3 am ni terminar después de las 10 pm
            if (fechaInicio.toLocalTime().isBefore(LocalDateTime.of(fechaInicio.toLocalDate(),
                    LocalDateTime.of(0, 1, 1, horaLimiteTarea, 0).toLocalTime()).toLocalTime()) ||
                    fechaFin.toLocalTime().isAfter(LocalDateTime.of(fechaFin.toLocalDate(),
                            LocalDateTime.of(0, 1, 1, horaLimiteFinalizacion, 0).toLocalTime()).toLocalTime())) {
                throw BusinessException.Type.ERROR_GUARDAR_TAREA_HORARIO_INVALIDO.build();
            }


            UbicacionesEntity ubicacion = ubicacionRepository.findDistinctTerritorios().stream().anyMatch(u -> u.getTerritorio().equals(request.getTerritorio())) ?
                    ubicacionRepository.findDistinctTerritorios().stream().filter(u -> u.getTerritorio().equals(request.getTerritorio())).findFirst().orElse(null) : null;


            if (ubicacion == null) {
                throw BusinessException.Type.ERROR_GUARDAR_TAREA_UBICACION_NO_EXISTE.build();
            }

            if (ubicacion.getTransporte().equals("NO") && (request.getTransporte() != null && request.getTransporte().equals("SI"))) {
                throw BusinessException.Type.ERROR_GUARDAR_TAREA_UBICACION_INACTIVA.build();
            }
        }
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


    @Override
    public List<TaskCheckResponse> getAllTasksByTerritorio(TaskFilterRequest filters) {

        int pagina = (filters.getPage() == null) ? 0 : Math.max(filters.getPage(), 0);
        int tamano = (filters.getTamano() == null) ? 2100 : Math.max(filters.getTamano(), 1);

        Pageable pageable = PageRequest.of(pagina, tamano);

        return getAllTasks(
                taskRepository.findAll(
                        TaskSpecifications.filtrarTareas(filters), pageable).getContent()
        );
    }

    @Override
    public TaskCheckResponse loadTaskAndEvidences(Long id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(BusinessException.Type.ERROR_TAREA_NO_EXISTE::build);

        return getAllTasks(List.of(task)).stream().findFirst().orElse(null);
    }

    private List<TaskCheckResponse> getAllTasks(List<TaskEntity> tasks) {


        // 1. Validar si la lista viene nula o vacía
        if (tasks == null || tasks.isEmpty()) {
            return new ArrayList<>();
        }

        return tasks.stream()
                .map(t ->
                        {
                            Responsable responsable = responsableName(t);

                            return TaskCheckResponse.builder()
                                    .id(t.getId())
                                    .title(t.getTitle())
                                    .description(t.getDescription())
                                    .celular(responsable != null ? responsable.getCelular() : null)
                                    .start(convertTimeToList(t.getStartDate()))
                                    .end(convertTimeToList(t.getEndDate()))
                                    .equipo(t.getEquipo())
                                    .novedad(t.getNovedad())
                                    .perfiles(t.getPerfiles() != null ? Arrays.asList(t.getPerfiles().split(",")) : null)
                                    .ubicacion(t.getUbicacion())
                                    .territorio(t.getTerritorio())
                                    .db(t.getDb())
                                    .url("")
                                    .responsable(responsable)
                                    .transporte(t.getTransporte())
                                    .transporteDetalles(t.getTransporteDetalles())
                                    .lugarSalida(t.getLugarSalida())
                                    .tipoTransporte(t.getTipoTransporte())
                                    .numPasajeros(t.getNumPasajeros())
                                    .lugarLlegada(t.getLugarLlegada())
                                    .estado(t.getStatus())
                                    .transporteAprobado(t.getTransporteAprobado())
                                    .build();
                        }

                )
                .toList();
    }

    private Responsable responsableName(TaskEntity t) {
        if (t.getResponsable() == null) return null;

        Optional<ResponsableEntity> responsableNode = responsableRepository.findNombreById(t.getResponsable());
        return responsableNode.map( r -> Responsable
                .builder()
                .nombre(r.getNombres())
                .id(r.getId())
                .celular(r.getCelular())
                .build()).orElse(null);
    }

}