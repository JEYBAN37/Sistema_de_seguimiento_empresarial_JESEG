package com.jeseg.admin_system.canalizaciones.domain.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class ContenedorCache implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime ultimaActualizacion;
    private List<PersonasResponse> data;
}
