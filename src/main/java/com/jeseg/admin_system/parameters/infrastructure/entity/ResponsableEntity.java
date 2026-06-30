package com.jeseg.admin_system.parameters.infrastructure.entity;

import com.sun.jdi.PrimitiveValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.Id;
@Entity
@Data
@Builder
@Getter
@Setter
@Table(name = "responsables")
@AllArgsConstructor
@NoArgsConstructor
public class ResponsableEntity {
    @Id // <-- ESTO ES LO QUE FALTA (Obliga a Hibernate a identificar la fila)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombres;
    private String numero;
    private String profesion;
    private String celular;
    private String correo;
    private String ebs;
    private String contrato;
}
