package com.jeseg.admin_system.canalizaciones.domain.intreface;

import com.jeseg.admin_system.canalizaciones.domain.dto.AccionCuidadoRequest;

import java.util.List;

public interface AccionCuidadoInterface {
    void createAccionCuidado(List<AccionCuidadoRequest> request);
}
