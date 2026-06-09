package com.jeseg.admin_system.parameters.domain.interfaces;

import com.jeseg.admin_system.parameters.domain.dto.Parametros;
import com.jeseg.admin_system.parameters.domain.dto.Responsable;
import com.jeseg.admin_system.parameters.domain.dto.Ubicacion;

import java.util.List;

public interface ParameterInterface {
    List<Parametros> listarParametros();
    List<Responsable> listarResponsables();
    List<Ubicacion> listarUbicaciones();
}
