package com.jeseg.admin_system.task.domain.intreface;

import com.jeseg.admin_system.task.domain.dto.*;


import java.util.List;


public interface TaskInterface {
    TaskResponse saveTask(TaskCreateRequest request);
    List<TaskCheckResponse> getAllTasksByTerritorio (TaskFilterRequest request);
    TaskCheckResponse loadTaskAndEvidences(Long id);
}
