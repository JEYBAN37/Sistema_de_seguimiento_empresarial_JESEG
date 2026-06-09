package com.jeseg.admin_system.task.domain.usecase;

import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.document.domain.dto.DocumentCreateRequest;
import com.jeseg.admin_system.document.domain.dto.DocumentResponse;
import com.jeseg.admin_system.document.domain.intreface.DocumentInterface;
import com.jeseg.admin_system.task.domain.dto.*;
import com.jeseg.admin_system.task.domain.intreface.TaskInterface;
import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.intreface.UserInterface;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.jeseg.admin_system.task.domain.model.Convert.convertTime;

@AllArgsConstructor
public class TaskUseCase {
    private final TaskInterface taskInterface;
    private final DocumentInterface documentInterface;

    public TaskResponse createTask(TaskCreateRequest task) {
        task.setEstado("PENDIENTE APROBACION POR COORDINADORA");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fechaInicio = convertTime(task.getFechaInicio());

        if (fechaInicio != null && fechaInicio.isBefore(now)) {
            throw BusinessException.Type.ERROR_GUARDAR_TAREA_HORA_ANTERIOR_AL_DIA_ACTUAL.build();
        }

        return taskInterface.saveTask(task);
    }

    public List<TaskCheckResponse> getAllTasksNoRecurrence (TaskFilterRequest request) {
        return taskInterface.getAllTasksByTerritorio(request);
    }

    public TaskResponse loadTaskAndEvidences(DocumentCreateRequest taskCreateRequest) {

        TaskCreateRequest task = new TaskCreateRequest();
        task.setId(taskCreateRequest.getId());

        task.setEstado(taskCreateRequest.getStatus());

        TaskResponse taskResponse = taskInterface.saveTask(task);
        documentInterface.loadAnexos(taskCreateRequest);
        return taskResponse;
    }


    public TaskResponseXdocument getTask(Long taskId) {

        TaskCheckResponse baseTask = taskInterface.loadTaskAndEvidences(taskId);
        List<DocumentResponse> documents = documentInterface.getDocumentsByTaskId(taskId);
        return new TaskResponseXdocument(baseTask, documents);
    }

}
