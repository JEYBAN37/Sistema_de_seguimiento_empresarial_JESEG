package com.jeseg.admin_system.user.domain.dto;

import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeResponse;
import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyResponse;
import com.jeseg.admin_system.role.domain.dto.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPageAdminRequest {
    private List<RoleResponse> roles;
    private List<String> functionsAvailable;
    private List<HierarchyNodeResponse> hierarchies;
    private List<UserResponse> users;
}
