package com.jeseg.admin_system.canalizaciones.domain.usecase;

import com.jeseg.admin_system.canalizaciones.domain.dto.PersonaAskRequest;
import com.jeseg.admin_system.canalizaciones.domain.dto.PersonasResponse;
import com.jeseg.admin_system.canalizaciones.domain.intreface.PersonaInterface;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PersonaApiUseCase {
    private final PersonaInterface personaInterface;
    public List<PersonasResponse> getAll (PersonaAskRequest request){
        return personaInterface.sendPersonasCanalizacion(request);
    }
}
