package com.jeseg.admin_system.role.domain.model;
import com.jeseg.admin_system.company.domain.model.Company;
import lombok.Data;

@Data
public class Role {
    private String name; // ADMIN, JEFE, COORDINADOR
    private Long idCompany;
}
