package com.jeseg.admin_system.company.infrastructure.service;


import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.company.domain.dto.CompanyCreateRequest;
import com.jeseg.admin_system.company.domain.usecase.CompanyUseCases;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CompanyService {
    private final CompanyUseCases companyUseCases;

    public void saveCompany(CompanyCreateRequest companyCreateRequest) {
        try {
            companyUseCases.createCompany(companyCreateRequest);
        } catch (Exception e) {
            throw BusinessException.Type.ERROR_GUARDAR_COMPANIA.build(e);
        }
    }

}
