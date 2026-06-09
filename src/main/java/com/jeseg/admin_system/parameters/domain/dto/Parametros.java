package com.jeseg.admin_system.parameters.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Parametros {
    private List<String> resultado;
}
