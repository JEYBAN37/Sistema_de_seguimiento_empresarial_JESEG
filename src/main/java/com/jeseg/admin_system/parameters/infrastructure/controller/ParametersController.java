package com.jeseg.admin_system.parameters.infrastructure.controller;

import com.jeseg.admin_system.parameters.domain.dto.ParametersResponse;
import com.jeseg.admin_system.parameters.infrastructure.service.ParameterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parameters")
@AllArgsConstructor
public class ParametersController {
    private final ParameterService parameterService;

    @GetMapping("/cronograma")
     public ResponseEntity<ParametersResponse> getParametersCronograma() {
         return ResponseEntity.ok(parameterService.getParametersCronograma());
     }
}
