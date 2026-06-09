package com.jeseg.admin_system.parameters.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@Getter
@Setter
@Table(name = "ubicaciones")
@AllArgsConstructor // Genera constructor con todos los campos
@NoArgsConstructor
public class UbicacionesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String territorio;
    private String estado;
    private String transporte;
}
