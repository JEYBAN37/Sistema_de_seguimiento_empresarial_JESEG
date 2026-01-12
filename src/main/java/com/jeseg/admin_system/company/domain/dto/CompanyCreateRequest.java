package com.jeseg.admin_system.company.domain.dto;
import lombok.Data;

@Data
public class CompanyCreateRequest {
    private String name;
    private String logoUrl;
    private String color;
}
