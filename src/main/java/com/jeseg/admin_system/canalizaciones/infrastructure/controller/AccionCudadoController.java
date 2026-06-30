package com.jeseg.admin_system.canalizaciones.infrastructure.controller;

import com.jeseg.admin_system.canalizaciones.domain.dto.AccionCuidadoRequest;
import com.jeseg.admin_system.canalizaciones.infrastructure.service.AccionCuidadoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/accion-cuidado")
@AllArgsConstructor
public class AccionCudadoController {
    private final AccionCuidadoService accionCuidadoService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createAccionCuidado(@Valid @RequestBody List<AccionCuidadoRequest> request) {
        accionCuidadoService.createAccionCuidado(request);
    }
}
