package com.jeseg.admin_system.parameters.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ParametersResponse {
    private final List<String> equipos;
    private final List<UbicacionResponse> territorios;
    private final List<String> actividades;
    private final List<Responsable> responsables;
    private final List<String> lugares;
}
