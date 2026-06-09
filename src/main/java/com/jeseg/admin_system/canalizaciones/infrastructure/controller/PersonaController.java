package com.jeseg.admin_system.canalizaciones.infrastructure.controller;


import com.jeseg.admin_system.canalizaciones.domain.dto.PersonaAskRequest;
import com.jeseg.admin_system.canalizaciones.domain.dto.PersonasResponse;
import com.jeseg.admin_system.canalizaciones.infrastructure.entity.Persona;
import com.jeseg.admin_system.canalizaciones.infrastructure.repository.PersonaRepository;
import com.jeseg.admin_system.canalizaciones.infrastructure.service.PersonaService;
import com.jeseg.admin_system.company.domain.dto.CompanyCreateRequest;
import com.jeseg.admin_system.company.domain.dto.CompanyResponse;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import com.jeseg.admin_system.company.infrastructure.service.CompanyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personas")
@AllArgsConstructor
public class PersonaController {
    private final PersonaService personaService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<PersonasResponse>> getCompany (@Valid @RequestBody PersonaAskRequest personaAskRequest){
        return ResponseEntity.ok(personaService.allPersonas(personaAskRequest));
    }
}
