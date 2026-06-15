package com.jeseg.admin_system.canalizaciones.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PersonasResponse {
    private Long id;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String tipoDocumento;
    private String numeroDocumento;
    private String fechaNacimiento;
    private String canalizaciones;
    private String sexo;
    private String aceptaFormulario;
    private String deteccionTemprana;
    private String rias;
    private String urgencia;
    private String aseguradora;
    private String regimen;
    private String ips;
    private String nombreAcudiente;
    private String telefonoAcudiente;
    private String telefono;
    private String estado;
    private String observacionIps;
    private String fechaRegistro;
    private String direccion;
    private String barrio;
}
