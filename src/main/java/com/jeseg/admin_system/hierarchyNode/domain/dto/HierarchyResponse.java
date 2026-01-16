package com.jeseg.admin_system.hierarchyNode.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HierarchyResponse {
    private Long id;
    private String nombre;
}
