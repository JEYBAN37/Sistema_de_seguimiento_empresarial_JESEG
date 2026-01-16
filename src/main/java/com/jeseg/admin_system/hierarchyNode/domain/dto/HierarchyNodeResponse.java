package com.jeseg.admin_system.hierarchyNode.domain.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class HierarchyNodeResponse {
    private String nombre;
    private Long plazas;
    private Long ocupadas;
    private Long disponibles;

}
