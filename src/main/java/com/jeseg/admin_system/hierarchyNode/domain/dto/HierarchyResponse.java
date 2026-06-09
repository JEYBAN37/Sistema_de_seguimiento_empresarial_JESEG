package com.jeseg.admin_system.hierarchyNode.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HierarchyResponse {
    private Long id;
    private String nombre;
}
