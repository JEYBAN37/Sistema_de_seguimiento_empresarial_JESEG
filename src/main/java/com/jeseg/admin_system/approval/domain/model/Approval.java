package com.jeseg.admin_system.approval.domain.model;
import lombok.Data;

@Data
public class Approval {
    private String name; // ADMIN, JEFE, COORDINADOR
    private Long idCompany;
}
