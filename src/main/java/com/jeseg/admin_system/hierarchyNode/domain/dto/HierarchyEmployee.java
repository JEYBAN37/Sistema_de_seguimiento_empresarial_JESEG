package com.jeseg.admin_system.hierarchyNode.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HierarchyEmployee {
    private String name;
    private Integer parent;
    private Integer quantity;
}
