package com.jeseg.admin_system.task.domain.intreface;

import com.jeseg.admin_system.task.domain.dto.TaskCheckResponse;
import com.jeseg.admin_system.task.domain.dto.TaskCreateRequest;
import com.jeseg.admin_system.task.domain.dto.TaskFiltersRequest;
import com.jeseg.admin_system.task.domain.dto.TaskResponse;


import java.util.List;


public interface TaskInterface {
    TaskResponse saveTask(TaskCreateRequest request);
    List<TaskCheckResponse> getAllTasksByHerarchyId (TaskFiltersRequest request);
}
