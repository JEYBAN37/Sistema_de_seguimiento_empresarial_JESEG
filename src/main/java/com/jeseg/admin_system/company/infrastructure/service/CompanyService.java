package com.jeseg.admin_system.company.infrastructure.service;

import com.jeseg.admin_system.company.domain.dto.CompanyCreateRequest;
import com.jeseg.admin_system.company.domain.dto.CompanyResponse;
import com.jeseg.admin_system.company.domain.usecase.CompanyUseCases;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CompanyService {
    private final CompanyUseCases companyUseCases;

    public CompanyResponse saveCompany(CompanyCreateRequest companyCreateRequest) {
            return companyUseCases.createCompany(companyCreateRequest);
    }

    public List<CompanyEntity> allCompanies(){
        return companyUseCases.getAll();
    }

}
