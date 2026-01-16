package com.jeseg.admin_system.role.domain.intreface;
import com.jeseg.admin_system.role.domain.dto.RoleCreateRequest;
import com.jeseg.admin_system.role.domain.dto.RoleResponse;

import java.util.List;

public interface RoleInterface {
    void createRole(RoleCreateRequest role );
    List<RoleResponse> foundRolesXCompany(Long idCompany);
}
