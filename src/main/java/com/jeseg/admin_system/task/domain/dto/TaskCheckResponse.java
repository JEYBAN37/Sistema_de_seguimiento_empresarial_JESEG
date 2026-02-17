package com.jeseg.admin_system.task.domain.dto;

import com.jeseg.admin_system.hierarchyNode.domain.dto.NodeResponse;
import com.jeseg.admin_system.task.infrastructure.entity.TaskScheduleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCheckResponse {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String priority;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<NodeResponse> assignedNodes;
    private List<NodeResponse> approverNodes;
    private TaskScheduleEntity schedule;
}
