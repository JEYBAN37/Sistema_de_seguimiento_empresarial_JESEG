package com.jeseg.admin_system.canalizaciones.domain.intreface;
import com.jeseg.admin_system.canalizaciones.domain.dto.ContenedorCache;
import com.jeseg.admin_system.canalizaciones.domain.dto.PersonaAskRequest;
import com.jeseg.admin_system.canalizaciones.domain.dto.PersonaUpdateState;
import com.jeseg.admin_system.canalizaciones.domain.dto.PersonasResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface PersonaInterface {
    List<PersonasResponse> sendPersonasCanalizacion(PersonaAskRequest filtros );
    void updateState(PersonaUpdateState request);
    long contarConFiltros(PersonaAskRequest filtros);
    List<PersonasResponse> obtenerTodoElDia(PersonaAskRequest filtros);
    ContenedorCache obtenerDeCache(String llave);
    void guardarEnCache(String llave, List<PersonasResponse> personasResponses, LocalDateTime ultimaActualizacion);
    List<PersonasResponse> obtenerNuevosDesde(LocalDateTime ultimoCheck ,PersonaAskRequest filtros);

}
