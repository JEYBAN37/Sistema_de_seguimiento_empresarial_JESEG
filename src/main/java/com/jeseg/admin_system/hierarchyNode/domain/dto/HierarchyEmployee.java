package com.jeseg.admin_system.hierarchyNode.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // VITAL para Jackson
@AllArgsConstructor
@Builder
public class HierarchyEmployee {
    private String name;
    private String parent; // En tu JSON viene como "1", así que String está bien
    private Integer quantity;
}