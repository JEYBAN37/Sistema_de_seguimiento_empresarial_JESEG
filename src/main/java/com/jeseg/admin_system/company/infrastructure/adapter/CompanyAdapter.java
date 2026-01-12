package com.jeseg.admin_system.company.infrastructure.adapter;
import com.jeseg.admin_system.company.domain.dto.CompanyCreateRequest;

import com.jeseg.admin_system.company.domain.intreface.CompanyInterface;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import com.jeseg.admin_system.company.infrastructure.mapper.CompanyMapper;
import com.jeseg.admin_system.company.infrastructure.repository.CompanyRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CompanyAdapter implements CompanyInterface {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public void createCompany(CompanyCreateRequest company) {
        CompanyEntity object = companyMapper.toEntity(company);
        companyRepository.save(object);
    }

    @Override
    public List<CompanyEntity> allCompanies() {
        return companyRepository.findAll();
    }

}
