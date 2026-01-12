package com.jeseg.admin_system.role.infrastructure.adapter;

import com.jeseg.admin_system.company.infrastructure.repository.CompanyRepository;
import com.jeseg.admin_system.role.domain.dto.RoleCreateRequest;
import com.jeseg.admin_system.role.domain.intreface.RoleInterface;
import com.jeseg.admin_system.role.domain.model.Role;
import com.jeseg.admin_system.role.infrastructure.entity.RoleEntity;
import com.jeseg.admin_system.role.infrastructure.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class RoleAdapter implements RoleInterface {

    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;

    @Override
    public void createRole(RoleCreateRequest roleCreateRequest) {
        roleRepository.save(RoleEntity.builder()
                            .name(roleCreateRequest.getName())
                            .company(companyRepository.getReferenceById(roleCreateRequest.getCompany()))
                            .build());
    }

    @Override
    public List<Role> foundRolesXCompany(Long idCompany) {
        return List.of();
    }
}
