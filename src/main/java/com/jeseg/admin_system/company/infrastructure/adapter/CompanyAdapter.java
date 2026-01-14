package com.jeseg.admin_system.company.infrastructure.adapter;
import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.company.domain.dto.CompanyCreateRequest;

import com.jeseg.admin_system.company.domain.dto.CompanyResponse;
import com.jeseg.admin_system.company.domain.intreface.CompanyInterface;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;import com.jeseg.admin_system.company.infrastructure.repository.CompanyRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
                .logoUrl(uploadedFiles(company.getFile()))
                .color(company.getColor())
                .name(company.getName()).build());

        return CompanyResponse.builder()
                .id(companySave.getId())
                .build();
    }


    private String uploadedFiles(MultipartFile file) {

        if (file == null || file.isEmpty()) {
           throw BusinessException.Type.ERROR_GUARDAR_COMPANIA_LOGO_VACIO.build();
        }

        try {
            String uploadDir = "uploads/logos/";
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);

            // 2. Guardar el archivo físicamente
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            // 3. Crear la URL para la base de datos
            return "/images/" + fileName;
        } catch (Exception e) {
            throw BusinessException.Type.ERROR_GUARDAR_COMPANIA.build(e);
        }
    }

    @Override
    public List<CompanyEntity> allCompanies() {
        return companyRepository.findAll();
    }

}
