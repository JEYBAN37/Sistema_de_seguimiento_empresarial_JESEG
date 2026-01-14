package com.jeseg.admin_system.task.domain.usecase;

import com.jeseg.admin_system.task.domain.dto.TaskCreateRequest;
import com.jeseg.admin_system.task.domain.intreface.TaskInterface;
import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.intreface.UserInterface;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TaskUseCase {
    private final TaskInterface taskInterface;
    public void createTask(TaskCreateRequest task) {
        taskInterface.saveTask(task);
    }
}
