package com.jeseg.admin_system.task.domain.intreface;

import com.jeseg.admin_system.task.domain.dto.TaskCreateRequest;


public interface TaskInterface {
    void saveTask(TaskCreateRequest request);
}
