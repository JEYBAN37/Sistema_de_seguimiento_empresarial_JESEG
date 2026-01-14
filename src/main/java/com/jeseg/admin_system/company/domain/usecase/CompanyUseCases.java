package com.jeseg.admin_system.company.domain.usecase;

import com.jeseg.admin_system.company.domain.dto.CompanyCreateRequest;
import com.jeseg.admin_system.company.domain.dto.CompanyResponse;
import com.jeseg.admin_system.company.domain.intreface.CompanyInterface;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CompanyUseCases {

    private final CompanyInterface companyInterface;

    public CompanyResponse createCompany(CompanyCreateRequest companyCreateRequest) {
        return companyInterface.createCompany(companyCreateRequest);
    }

    public List<CompanyEntity> getAll (){
       return companyInterface.allCompanies();
    }
}
