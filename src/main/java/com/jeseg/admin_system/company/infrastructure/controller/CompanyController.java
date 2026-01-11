package com.jeseg.admin_system.company.infrastructure.controller;


import com.jeseg.admin_system.company.domain.dto.CompanyCreateRequest;
import com.jeseg.admin_system.company.infrastructure.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
@AllArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("/create")
    public void createCompany(CompanyCreateRequest companyCreateRequest) {
        companyService.saveCompany( companyCreateRequest);
    }
}
