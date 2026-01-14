package com.jeseg.admin_system.task.infrastructure.adapter;

import com.jeseg.admin_system.company.infrastructure.repository.CompanyRepository;
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

    @Override
    public void saveTask(TaskCreateRequest request) {
        taskRepository.save(TaskEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .approvalRequired(request.getApprovalRequired())
                .company(companyRepository.getReferenceById(request.getCompany()))
                .createdBy(userRepository.getReferenceById(request.getCreatedBy()))
                .build());
    }
}
