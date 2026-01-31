package com.jeseg.admin_system.task.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class TaskFiltersRequest {
    private Long herarchyId;
    private Long assignedUserId;
    private String status;
    private String createAt;
    private String title;
    private String priority;
    private Boolean recurrence;
    private String startDate;
    private String endDate;
}
