package com.jeseg.admin_system.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor // Genera constructor con todos los campos
@NoArgsConstructor
public class UserCreateRequest {

    private String username;
    private String password;
    private String nombre;
    private String cedula;
    private String telefono;
    private String contrato;
    private Long company;
    private Long role;
}
