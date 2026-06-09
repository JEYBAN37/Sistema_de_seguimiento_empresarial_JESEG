package com.jeseg.admin_system.company.domain.intreface;
import com.jeseg.admin_system.company.domain.dto.CompanyCreateRequest;
import com.jeseg.admin_system.company.domain.dto.CompanyResponse;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;

import java.util.List;

public interface CompanyInterface {
    CompanyResponse createCompany(CompanyCreateRequest company );
    List<CompanyEntity> allCompanies();
}
