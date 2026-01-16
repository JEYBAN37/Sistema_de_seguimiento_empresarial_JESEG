package com.jeseg.admin_system.hierarchyNode.domain.intreface;

import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeCreateRequest;
import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeResponse;

import java.util.List;

public interface HierarchyNodeInterface {
    void saveHierarchy(HierarchyNodeCreateRequest hierarchy);
    List<HierarchyNodeResponse> getResumenHierarchy (Long id);
}
