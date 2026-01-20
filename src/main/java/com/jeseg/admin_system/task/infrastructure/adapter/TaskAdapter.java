package com.jeseg.admin_system.task.infrastructure.adapter;

import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import com.jeseg.admin_system.company.infrastructure.repository.CompanyRepository;
import com.jeseg.admin_system.document.infrastructure.repository.DocumentRepository;
import com.jeseg.admin_system.hierarchyNode.domain.intreface.HierarchyNodeInterface;
import com.jeseg.admin_system.hierarchyNode.infrastructure.entity.HierarchyNodeEntity;
import com.jeseg.admin_system.hierarchyNode.infrastructure.repository.HierarchyNodeRepository;
import com.jeseg.admin_system.task.domain.dto.TaskCreateRequest;
import com.jeseg.admin_system.task.domain.intreface.TaskInterface;


import com.jeseg.admin_system.task.infrastructure.entity.TaskEntity;
import com.jeseg.admin_system.task.infrastructure.repository.TaskRepository;
import com.jeseg.admin_system.user.infrastructure.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TaskAdapter implements TaskInterface {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final HierarchyNodeRepository hierarchyNodeRepository;
    private final DocumentRepository documentRepository;

    @Override
    public void saveTask(TaskCreateRequest request) {


        CompanyEntity company = companyRepository.findById(request.getCompany()).orElseThrow(
                BusinessException.Type.ERROR_GUARDAR_HIERARCHY_COMPANIA_NO_EXISTE::build
        );

        HierarchyNodeEntity node = hierarchyNodeRepository.findById(request.getCreatedBy()).orElseThrow(
                BusinessException.Type.ERROR_GUARDAR_TAREA_NODO_NO_EXISTE::build
        );

        // Guardar la tarea tarea // verificar si tiene adjuntos
        TaskEntity task = taskRepository.save(TaskEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .approvalRequired(request.getApprovalRequired() != null)
                .company(company)
                .createdBy(node)
                .createdAt(request.getCreatedAt())
                .createdAt(request.getStartDate())
                .endDate(request.getEndDate())
                .priority(null)
                .build());




        // guardar si es de cronograma o no


        // guardas las asignaciones


        taskRepository.save(TaskEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .approvalRequired(null)
                .company(companyRepository.getReferenceById(request.getCompany()))
                .createdBy(null)
                .build());
    }
}
