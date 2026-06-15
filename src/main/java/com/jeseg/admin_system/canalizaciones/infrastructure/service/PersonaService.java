package com.jeseg.admin_system.canalizaciones.infrastructure.service;

import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.canalizaciones.domain.dto.PersonaAskRequest;
import com.jeseg.admin_system.canalizaciones.domain.dto.PersonaUpdateState;
import com.jeseg.admin_system.canalizaciones.domain.dto.PersonasResponse;
import com.jeseg.admin_system.canalizaciones.domain.usecase.PersonaApiUseCase;
import com.jeseg.admin_system.canalizaciones.infrastructure.entity.Persona;
import com.jeseg.admin_system.company.domain.dto.CompanyCreateRequest;
import com.jeseg.admin_system.company.domain.dto.CompanyResponse;
import com.jeseg.admin_system.company.domain.usecase.CompanyUseCases;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

import static com.jeseg.admin_system.application.ex.BusinessException.Type.ERROR_OPCIONES_FILTRO_PERSONAS_INVALIDAS;

@AllArgsConstructor
@Service
public class PersonaService {
    private final PersonaApiUseCase personaApiUseCase;

    public List<PersonasResponse> allPersonas(PersonaAskRequest request) {
        return personaApiUseCase.getAll(request);
    }

    public void updateState(PersonaUpdateState request) {

        if (
                request.getEstado() == null ||
                        request.getEstado().isBlank() || request.getId() == null || request.getId() <= 0
        ) {
            throw BusinessException.Type.ERROR_OPCIONES_FILTRO_PERSONAS_INVALIDAS.build();
        }
        personaApiUseCase.updateState(request);
    }

}
