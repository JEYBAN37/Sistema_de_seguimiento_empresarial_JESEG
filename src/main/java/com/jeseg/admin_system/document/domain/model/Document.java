package com.jeseg.admin_system.document.domain.model;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Document {
    private String id; // ADMIN, JEFE, COORDINADOR
    private Long idCompany;
}
