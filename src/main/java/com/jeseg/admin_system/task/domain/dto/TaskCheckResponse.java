package com.jeseg.admin_system.task.domain.dto;

import com.jeseg.admin_system.hierarchyNode.domain.dto.NodeResponse;
import com.jeseg.admin_system.task.infrastructure.entity.TaskScheduleEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskCheckResponse {
    private Long id;
    private String title;
    private List<Integer> start;
    private List<Integer> end;
    private String description;
    private String ubicacion;
    private List<CommendResponse> comments;
    private String status;

    private List<NodeResponse> approvalRequired;
    private List<NodeResponse> assignedNodes;
}

