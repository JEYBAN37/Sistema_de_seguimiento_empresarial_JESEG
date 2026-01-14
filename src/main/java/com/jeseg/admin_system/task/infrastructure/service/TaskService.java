package com.jeseg.admin_system.task.infrastructure.service;


import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.task.domain.dto.TaskCreateRequest;
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

    public void saveTask(TaskCreateRequest taskCreateRequest) {
        try {
            taskUseCase.createTask(taskCreateRequest);
        } catch (Exception e) {
            throw BusinessException.Type.ERROR_GUARDAR_TASK.build(e);
        }
    }

}
