package com.jeseg.admin_system.company.infrastructure.controller;


import com.jeseg.admin_system.company.domain.dto.CompanyCreateRequest;
import com.jeseg.admin_system.company.domain.dto.CompanyResponse;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import com.jeseg.admin_system.company.infrastructure.service.CompanyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/companies")
@AllArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyResponse> createCompany(@Valid @ModelAttribute CompanyCreateRequest companyCreateRequest) {
        CompanyResponse response = companyService.saveCompany(companyCreateRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<CompanyEntity>> getCompany (){
        return ResponseEntity.ok(companyService.allCompanies());
    }
}
