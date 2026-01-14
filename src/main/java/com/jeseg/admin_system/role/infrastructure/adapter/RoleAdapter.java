package com.jeseg.admin_system.role.infrastructure.adapter;

import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.company.infrastructure.repository.CompanyRepository;
import com.jeseg.admin_system.role.domain.dto.RoleCreateRequest;
import com.jeseg.admin_system.role.domain.dto.RoleItemRequest;
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
        List<String> names = roleCreateRequest.getName().stream()
                .map(RoleItemRequest::getName)
                .toList();
        validarNames(names, roleCreateRequest.getCompany());

        roleRepository.saveAll(roles(roleCreateRequest.getCompany(), names));
    }

    private void validarNames (List<String> names, Long companyId) {
        long distintos = names.stream().distinct().count();

        if (names.isEmpty()) {
            throw BusinessException.Type.LISTADO_VACIO_ROLE.build();
        }

        if (distintos < names.size()) {
            throw BusinessException.Type.ERROR_GUARDAR_ROLE_NOMBRE_REPETIDO.build();
        }

        boolean yaExisten = roleRepository.existsByNameInAndCompanyId(names,companyId);
        if (yaExisten) {
            throw BusinessException.Type.ROLE_YA_EXISTE_EN_COMPANIA.build();
        }
    }

    private List<RoleEntity> roles (Long companyId, List<String> roleNames) {
        return roleNames.stream()
                .map(name -> RoleEntity.builder()
                        .name(name)
                        .company(companyRepository.getReferenceById(companyId))
                        .build())
                .toList();
    }


    @Override
    public List<Role> foundRolesXCompany(Long idCompany) {
        return List.of();
    }
}
