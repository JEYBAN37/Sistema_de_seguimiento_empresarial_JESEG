package com.jeseg.admin_system.canalizaciones.domain.dto;

import com.jeseg.admin_system.parameters.domain.dto.Responsable;
import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@RequestMapping
public class AccionCuidadoRequest {
    private Long id;
    private String numeroDoc;
    private String situacionesPriorizadas;
    private String logrosAlcanzados;
    private String responsableFamilia;
    private LocalDate fechaCompromiso;
    private LocalDate fechaSeguimiento;
    private String estado;
    private String responsableSeguimiento;
}
