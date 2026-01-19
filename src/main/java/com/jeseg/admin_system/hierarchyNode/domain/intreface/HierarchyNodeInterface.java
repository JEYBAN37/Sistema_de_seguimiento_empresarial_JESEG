package com.jeseg.admin_system.hierarchyNode.domain.intreface;

import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeCreateRequest;
import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeResponse;
import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeTaskRequest;
import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeTaskResponse;
import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;

import java.util.List;

public interface HierarchyNodeInterface {
    void saveHierarchy(HierarchyNodeCreateRequest hierarchy);
    List<HierarchyNodeResponse> getResumenHierarchy (Long id);
    HierarchyNodeTaskResponse getStructureTask (Long idHierarchyNode);
}
