package com.jeseg.admin_system.company.infrastructure.adapter;
import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.company.domain.dto.CompanyCreateRequest;

import com.jeseg.admin_system.company.domain.dto.CompanyResponse;
import com.jeseg.admin_system.company.domain.intreface.CompanyInterface;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;import com.jeseg.admin_system.company.infrastructure.repository.CompanyRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.jeseg.admin_system.application.UploadsGeneric.uploadedFiles;

@Repository
@AllArgsConstructor
public class CompanyAdapter implements CompanyInterface {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyResponse createCompany(CompanyCreateRequest company)  {

        if (companyRepository.existsByName(company.getName())) {
            throw BusinessException.Type.ERROR_GUARDAR_COMPANIA_NOMBRE_REPETIDO.build();
        }
        CompanyEntity companySave = companyRepository.save(CompanyEntity.builder()
                .logoUrl(uploadedFiles(company.getFile(),"uploads/logos/"))
                .color(company.getColor())
                .name(company.getName()).build());

        return CompanyResponse.builder()
                .id(companySave.getId())
                .build();
    }


    @Override
    public List<CompanyEntity> allCompanies() {
        return companyRepository.findAll();
    }

}
