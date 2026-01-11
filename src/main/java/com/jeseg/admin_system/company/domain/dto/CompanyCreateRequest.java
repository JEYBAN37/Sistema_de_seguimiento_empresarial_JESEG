package com.jeseg.admin_system.company.domain.dto;
import lombok.Data;

@Data
public class CompanyCreateRequest {
    String name;
    String users;
    String logoUrl;
    String color;
}
