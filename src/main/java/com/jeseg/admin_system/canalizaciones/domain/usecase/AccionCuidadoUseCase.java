package com.jeseg.admin_system.canalizaciones.domain.usecase;

import com.jeseg.admin_system.canalizaciones.domain.dto.AccionCuidadoRequest;
import com.jeseg.admin_system.canalizaciones.domain.intreface.AccionCuidadoInterface;
import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
public class AccionCuidadoUseCase {
    private final AccionCuidadoInterface accionCuidadoInterface;

    public void create(List<AccionCuidadoRequest> request) {
        accionCuidadoInterface.createAccionCuidado(request);
    }
}
