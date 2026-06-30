package com.jeseg.admin_system.canalizaciones.infrastructure.service;

import com.jeseg.admin_system.canalizaciones.domain.dto.AccionCuidadoRequest;
import com.jeseg.admin_system.canalizaciones.domain.usecase.AccionCuidadoUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AccionCuidadoService {

    private final AccionCuidadoUseCase accionCuidadoUseCase;
    public void createAccionCuidado(List<AccionCuidadoRequest> request) {
        accionCuidadoUseCase.create(request);
    }
}
