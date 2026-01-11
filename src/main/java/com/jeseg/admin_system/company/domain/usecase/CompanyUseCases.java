package com.jeseg.admin_system.company.domain.usecase;

import com.jeseg.admin_system.company.domain.dto.CompanyCreateRequest;
import com.jeseg.admin_system.company.domain.intreface.CompanyInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CompanyUseCases {

    private final CompanyInterface companyInterface;

    public void createCompany(CompanyCreateRequest companyCreateRequest) {
        companyInterface.createCompany(companyCreateRequest);
    }
}
