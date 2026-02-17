package com.jeseg.admin_system.task.domain.dto;
import com.jeseg.admin_system.hierarchyNode.domain.dto.NodeResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class CommendResponse {
    private String comment;
    private List<Integer> createdAt;
    private String createdBy;
}
