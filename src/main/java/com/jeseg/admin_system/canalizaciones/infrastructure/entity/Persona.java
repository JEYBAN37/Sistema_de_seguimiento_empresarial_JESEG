package com.jeseg.admin_system.canalizaciones.infrastructure.entity;
import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@Getter
@Setter
@Table(name = "personas")
@AllArgsConstructor // Genera constructor con todos los campos
@NoArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "primernombre")
    private String primerNombre;

    @Column(name = "segundonombre")
    private String segundoNombre;

    @Column(name = "primerapellido")
    private String primerApellido;

    @Column(name = "segundoapellido")
    private String segundoApellido;

    @Column(name = "tipodocumento")
    private String tipoDocumento;

    @Column(name = "numerodoc")
    private String numeroDoc;

    @Column(name = "fechanac")
    private String fechaNacimiento;

    @Column(name = "canalizacionuno")
    private String canalizaciones;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "aceptaformulario")
    private String aceptaFormulario;

    @Column(name = "detecciontemprana")
    private String deteccionTemprana;

    @Column(name = "rias")
    private String rias;

    @Column(name = "urgencia")
    private String urgencia;

    @Column(name = "aseguradora")
    private String aseguradora;

    @Column(name = "regimen")
    private String regimen;

    @Column(name = "estado")
    private String estado;

    @Column(name = "canalizacion_id")
    private String ips;

    @Column(name = "nombreAcudiente")
    private String nombreAcudiente;

    @Column(name = "telefonoAcudiente")
    private String telefonoAcudiente;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "observacionIps")
    private String observacionIps;

    @Column(name = "fecharegistro")
    private LocalDateTime fechaRegistro;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "barriovereda")
    private String barrioVereda;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccionCuidado> acciones;


    //@Column(name = "direccion")
    //private String direccion;

}