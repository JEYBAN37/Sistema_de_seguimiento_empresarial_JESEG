package com.jeseg.admin_system.canalizaciones.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PersonaAskRequest {
    private String busqueda;
    private Integer page;
    private Integer tamano;
    //private String canalizacion_id;
    private String rias;
    //private String pic;
    private String urgencia;
    //private String atencion;
    //private String caracterizar;
    private String aceptaFormulario;
    private String deteccionTemprana;
}
