package com.jeseg.admin_system.hierarchyNode.infrastructure.service;


import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeCreateRequest;
import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeResponse;
import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeTaskRequest;
import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeTaskResponse;
import com.jeseg.admin_system.hierarchyNode.domain.usecase.HierarchyNodeUseCase;
import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.usecase.UserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class HierarchyNodeService {
    private final HierarchyNodeUseCase hierarchyNodeUseCase;

    public void saveHierachy(HierarchyNodeCreateRequest hierarchyNodeCreateRequest) {
            hierarchyNodeUseCase.createHierarchy(hierarchyNodeCreateRequest);
    }

    public  List<HierarchyNodeResponse> getStructureHierachy(Long companyId) {
        return hierarchyNodeUseCase.getStructure(companyId);
    }

    public HierarchyNodeTaskResponse getFlatStructure(Long request) {
        return hierarchyNodeUseCase.getFlatStructure(request);
    }

}
