package com.jeseg.admin_system.task.domain.dto;

import com.jeseg.admin_system.parameters.domain.dto.Responsable;
import com.jeseg.admin_system.task.infrastructure.entity.TaskCommentEntity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateRequest {
    private Long id;
    private String titulo;
    private String fechaInicio;
    private String fechaFin;
    private String equipo;
    private String descripcion;
    private String novedad;
    private List<String> perfiles;
    private String ubicacion;
    private Responsable responsable;
    private String territorio;
    private String db;
    private String transporte;
    private String transporteDetalles;
    private String lugarSalida;
    private String tipoTransporte;
    private String numPasajeros;
    private String lugarLlegada;
    private String estado;
}
