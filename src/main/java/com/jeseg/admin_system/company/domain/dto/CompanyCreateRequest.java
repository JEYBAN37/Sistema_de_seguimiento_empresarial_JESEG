package com.jeseg.admin_system.company.domain.dto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CompanyCreateRequest {
    private String name;
    private MultipartFile file;
    private String color;
}
