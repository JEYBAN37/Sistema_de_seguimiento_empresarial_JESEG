package com.jeseg.admin_system.role.domain.dto;
import lombok.Data;

@Data
public class RoleCreateRequest {
    private String name; // ADMIN, JEFE, COORDINADOR
    private Long company;
}
