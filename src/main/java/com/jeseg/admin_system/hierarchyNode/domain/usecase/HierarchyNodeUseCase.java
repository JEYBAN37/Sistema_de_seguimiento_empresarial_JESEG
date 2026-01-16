package com.jeseg.admin_system.hierarchyNode.domain.usecase;

import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeCreateRequest;
import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeResponse;
import com.jeseg.admin_system.hierarchyNode.domain.intreface.HierarchyNodeInterface;
import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.intreface.UserInterface;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class HierarchyNodeUseCase {
    private final HierarchyNodeInterface hierarchyNodeInterface;

    public void createHierarchy(HierarchyNodeCreateRequest hierarchyNodeCreateRequest){
        hierarchyNodeInterface.saveHierarchy(hierarchyNodeCreateRequest);
    }

    public  List<HierarchyNodeResponse> getStructure (Long id){
        return hierarchyNodeInterface.getResumenHierarchy(id);
    }


}

