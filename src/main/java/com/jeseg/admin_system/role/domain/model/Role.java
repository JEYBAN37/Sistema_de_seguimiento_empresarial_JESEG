package com.jeseg.admin_system.role.domain.model;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {
    private Long id;
    private String name; // ADMIN, JEFE, COORDINADOR
    private Long company;
}
