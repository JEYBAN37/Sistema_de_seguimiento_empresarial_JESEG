package com.jeseg.admin_system.parameters.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@Getter
@Setter
@Table(name = "parametros")
@AllArgsConstructor // Genera constructor con todos los campos
@NoArgsConstructor
public class ParametersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String resultado;
    private String indicador;
    private String curso;
    private String tipo;
}
