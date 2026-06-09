package com.jeseg.admin_system.canalizaciones.infrastructure.service;

import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.canalizaciones.domain.dto.PersonaAskRequest;
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

    public List<PersonasResponse> allPersonas(PersonaAskRequest request){
        return personaApiUseCase.getAll(request);
    }

    private void verificarFiltrosValidos(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            throw new IllegalArgumentException("El filtro no puede ser nulo o vacío");
        }

        Predicate<String> isNumeric = s -> s.matches("\\d+");

        if (!isNumeric.test(filtro)) {
            throw BusinessException.Type.ERROR_OPCIONES_FILTRO_PERSONAS_INVALIDAS.build();
        }

        if (!filtro.equalsIgnoreCase("1")) {
            throw new IllegalArgumentException("El filtro debe ser '1' para activar la condición");
        }
    }

}
