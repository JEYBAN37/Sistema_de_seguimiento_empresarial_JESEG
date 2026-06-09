package com.jeseg.admin_system.approval.infrastructure.controller;


import com.jeseg.admin_system.approval.domain.dto.ApprovalCreateRequest;
import com.jeseg.admin_system.approval.infrastructure.service.ApprovalService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/approval")
@AllArgsConstructor
public class ApprovalController {
    private final ApprovalService approvalService;

    @PostMapping("/create")
    public ResponseEntity<?> createApproval(@Valid @RequestBody ApprovalCreateRequest companyCreateRequest) {
        approvalService.saveApproval(companyCreateRequest);
        return ResponseEntity.ok(Collections.singletonMap("message", "Operación Exitosa"));
    }

}
