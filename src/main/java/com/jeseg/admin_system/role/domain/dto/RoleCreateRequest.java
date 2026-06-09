package com.jeseg.admin_system.role.domain.dto;
import lombok.Data;

import java.util.List;

@Data
public class RoleCreateRequest {
    private List<RoleItemRequest> name; // ADMIN, JEFE, COORDINADOR
    private Long company;
}

