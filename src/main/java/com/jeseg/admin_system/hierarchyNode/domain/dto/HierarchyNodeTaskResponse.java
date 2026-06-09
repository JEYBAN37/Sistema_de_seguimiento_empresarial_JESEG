package com.jeseg.admin_system.hierarchyNode.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HierarchyNodeTaskResponse {
   private List<NodeResponse> supervisores;
   private List<NodeResponse> subordinados;
}
