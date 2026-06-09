package com.jeseg.admin_system.canalizaciones.domain.intreface;
import com.jeseg.admin_system.canalizaciones.domain.dto.PersonaAskRequest;
import com.jeseg.admin_system.canalizaciones.domain.dto.PersonasResponse;
import com.jeseg.admin_system.company.domain.dto.CompanyCreateRequest;
import com.jeseg.admin_system.company.domain.dto.CompanyResponse;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;

import java.util.List;

public interface PersonaInterface {
    List<PersonasResponse> sendPersonasCanalizacion(PersonaAskRequest filtros );

}
