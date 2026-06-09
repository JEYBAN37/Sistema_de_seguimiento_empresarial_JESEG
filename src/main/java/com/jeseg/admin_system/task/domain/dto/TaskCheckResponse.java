package com.jeseg.admin_system.task.domain.dto;

import com.jeseg.admin_system.hierarchyNode.domain.dto.NodeResponse;
import com.jeseg.admin_system.parameters.domain.dto.Responsable;
import com.jeseg.admin_system.task.infrastructure.entity.TaskScheduleEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCheckResponse {
    private Long id;
    private String title;
    private List<Integer> start;
    private List<Integer> end;
    private String description;
    private String ubicacion;
    private String equipo;
    private String novedad;
    private List<String> perfiles;
    private String url;
    private String celular;
    private String territorio;
    private String db;


    private Responsable responsable;

    // variables de transporte

    private String transporte;
    private String transporteDetalles;
    private String lugarSalida;
    private String tipoTransporte;
    private String numPasajeros;
    private String lugarLlegada;
    private String estado;
    private String transporteAprobado;

    // private List<NodeResponse> approvalRequired;
    // private List<NodeResponse> assignedNodes;
}

