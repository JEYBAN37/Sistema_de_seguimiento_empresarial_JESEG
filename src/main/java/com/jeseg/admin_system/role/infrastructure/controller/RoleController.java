package com.jeseg.admin_system.role.infrastructure.controller;


import com.jeseg.admin_system.role.domain.dto.RoleCreateRequest;
import com.jeseg.admin_system.role.infrastructure.entity.RoleEntity;
import com.jeseg.admin_system.role.infrastructure.service.RoleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {
    private final RoleService companyService;

    @PostMapping("/create")
    public ResponseEntity<?> createCompany(@Valid @RequestBody RoleCreateRequest roleCreateRequest) {
        companyService.saveRole(roleCreateRequest);
        return ResponseEntity.ok(Collections.singletonMap("message", "Operación Exitosa"));
    }

}
