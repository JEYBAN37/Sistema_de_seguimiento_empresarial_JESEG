package com.jeseg.admin_system.role.infrastructure.service;


import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.role.domain.dto.RoleCreateRequest;
import com.jeseg.admin_system.role.domain.usecase.RoleUseCases;
import com.jeseg.admin_system.role.infrastructure.entity.RoleEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleService {
    private final RoleUseCases companyUseCases;

    public void saveRole(RoleCreateRequest roleCreateRequest) {
            companyUseCases.createRole(roleCreateRequest);

    }

}
