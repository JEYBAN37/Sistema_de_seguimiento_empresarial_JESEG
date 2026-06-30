package com.jeseg.admin_system.canalizaciones.domain.dto;

import com.jeseg.admin_system.parameters.domain.dto.Responsable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccionCuidadoResponse {
    private Long id;

    private String numeroDoc;
    private String situacionesPriorizadas;
    private String logrosAlcanzados;
    private String responsableFamilia;
    private LocalDate fechaCompromiso;
    private LocalDate fechaSeguimiento;
    private String seguimientoCompromiso;
    private String estado;
    private String responsableSeguimiento;

}
