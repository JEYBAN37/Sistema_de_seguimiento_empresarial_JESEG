package com.jeseg.admin_system.task.domain.dto;

import com.jeseg.admin_system.document.domain.dto.DocumentResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseXdocument extends TaskCheckResponse {
    private List<DocumentResponse> anexos;

    public TaskResponseXdocument(TaskCheckResponse taskCheckResponse, List<DocumentResponse> anexos) {
        super(taskCheckResponse.getId(), taskCheckResponse.getTitle(), taskCheckResponse.getStart(),
              taskCheckResponse.getEnd(), taskCheckResponse.getDescription(), taskCheckResponse.getUbicacion(),
              taskCheckResponse.getEquipo(), taskCheckResponse.getNovedad(), taskCheckResponse.getPerfiles(),
              taskCheckResponse.getUrl(), taskCheckResponse.getCelular(), taskCheckResponse.getTerritorio(),
              taskCheckResponse.getDb(), taskCheckResponse.getResponsable(), taskCheckResponse.getTransporte(),
              taskCheckResponse.getTransporteDetalles(), taskCheckResponse.getLugarSalida(),
              taskCheckResponse.getTipoTransporte(), taskCheckResponse.getNumPasajeros(),
              taskCheckResponse.getLugarLlegada(), taskCheckResponse.getEstado(),
              taskCheckResponse.getTransporteAprobado());
        this.anexos = anexos;
    }


    // private List<NodeResponse> approvalRequired;
    // private List<NodeResponse> assignedNodes;
}
