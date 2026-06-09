package com.jeseg.admin_system.parameters.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Ubicacion {
    private Long id;
    private String territorio;
    private String estado;
    private String transporte;
}
