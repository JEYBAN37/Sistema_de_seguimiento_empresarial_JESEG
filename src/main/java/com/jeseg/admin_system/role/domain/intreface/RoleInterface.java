package com.jeseg.admin_system.role.domain.intreface;
import com.jeseg.admin_system.role.domain.dto.RoleCreateRequest;
import com.jeseg.admin_system.role.domain.model.Role;
import com.jeseg.admin_system.role.infrastructure.entity.RoleEntity;

import java.util.List;

public interface RoleInterface {
    void createRole(RoleCreateRequest role );
    List<Role> foundRolesXCompany(Long idCompany);
}
