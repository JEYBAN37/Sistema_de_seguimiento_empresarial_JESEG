package com.jeseg.admin_system.user.domain.dto;

import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyResponse;
import com.jeseg.admin_system.role.domain.dto.RoleResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String nombreCompleto;
    private String cedula;
    private String telefono;
    private String contrato;
    private Boolean active;
    private RoleResponse role;
    private HierarchyResponse hierarchyNode;
    private LocalDate fechaInicioContrato;
    private LocalDate fechaTerminoContrato;
    private String objeto;
    private String numeroSupervisor;
    private String nombreSupervisor;
    private String pagado;
    private String valorContratado;
    private String idRecurso;
    private String saldo;
}
