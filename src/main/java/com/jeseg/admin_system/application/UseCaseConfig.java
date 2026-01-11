package com.jeseg.admin_system.application;

import com.jeseg.admin_system.company.domain.intreface.CompanyInterface;
import com.jeseg.admin_system.company.domain.usecase.CompanyUseCases;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CompanyUseCases companyUseCases(CompanyInterface companyInterface) {
        return new CompanyUseCases(companyInterface);
    }
}
