package com.jeseg.admin_system.canalizaciones.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDateTime fechaRegistro;
    private String direccion;
    private String barrio;
    private List<AccionCuidadoResponse> accionesCuidado;
}
