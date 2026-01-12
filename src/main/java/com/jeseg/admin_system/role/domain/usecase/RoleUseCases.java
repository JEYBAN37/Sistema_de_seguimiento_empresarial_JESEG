package com.jeseg.admin_system.role.domain.usecase;

import com.jeseg.admin_system.company.domain.intreface.CompanyInterface;
import com.jeseg.admin_system.role.domain.dto.RoleCreateRequest;
import com.jeseg.admin_system.role.domain.intreface.RoleInterface;
import com.jeseg.admin_system.role.domain.model.Role;
import com.jeseg.admin_system.role.infrastructure.entity.RoleEntity;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RoleUseCases {

    private final RoleInterface roleInterface;

    public void createRole(RoleCreateRequest roleCreateRequest) {
            roleInterface.createRole(roleCreateRequest);
    }

    public List<Role> getRoleXCompanies(Long idCompany){
        return roleInterface.foundRolesXCompany(idCompany);
    }
}
