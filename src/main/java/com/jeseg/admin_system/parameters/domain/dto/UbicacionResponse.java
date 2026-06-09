package com.jeseg.admin_system.parameters.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class UbicacionResponse {
    private String territorio;
    private String estado;
    private String transporte;

}
