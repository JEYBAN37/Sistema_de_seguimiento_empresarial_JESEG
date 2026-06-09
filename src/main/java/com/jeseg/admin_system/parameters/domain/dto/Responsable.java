package com.jeseg.admin_system.parameters.domain.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Responsable {
        private Long id;
        private String nombre;
        private String celular;
}
