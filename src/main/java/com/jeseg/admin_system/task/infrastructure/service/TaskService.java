package com.jeseg.admin_system.task.infrastructure.service;


import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.document.domain.dto.DocumentCreateRequest;
import com.jeseg.admin_system.parameters.domain.usecases.ParametersUseCase;
import com.jeseg.admin_system.task.domain.dto.*;
import com.jeseg.admin_system.task.domain.usecase.TaskUseCase;
import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.usecase.UserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TaskService {

    private final TaskUseCase taskUseCase;

    public TaskResponse saveTask(TaskCreateRequest taskCreateRequest) {
            return taskUseCase.createTask(taskCreateRequest);

    }

    public List<TaskCheckResponse> getTasksByUser(TaskFilterRequest taskFiltersRequest) {
        return taskUseCase.getAllTasksNoRecurrence(taskFiltersRequest);
    }

    public TaskResponse loadTaskAndEvidences(DocumentCreateRequest documentCreateRequest) {
        return taskUseCase.loadTaskAndEvidences(documentCreateRequest);
    }

    public TaskResponseXdocument getTask(Long taskId) {
        return taskUseCase.getTask(taskId);
    }

}
