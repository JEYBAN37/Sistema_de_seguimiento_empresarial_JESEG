package com.jeseg.admin_system.hierarchyNode.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HierarchyNodeCreateRequest {
    private Long company;
    private List<HierarchyEmployee> jerarquias;
}