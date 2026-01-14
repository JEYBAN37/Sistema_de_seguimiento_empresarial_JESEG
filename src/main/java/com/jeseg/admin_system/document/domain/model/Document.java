package com.jeseg.admin_system.document.domain.model;
import lombok.Data;

@Data
public class Document {
    private String name; // ADMIN, JEFE, COORDINADOR
    private Long idCompany;
}
