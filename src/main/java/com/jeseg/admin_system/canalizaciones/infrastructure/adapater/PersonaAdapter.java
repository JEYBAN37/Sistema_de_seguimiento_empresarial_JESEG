package com.jeseg.admin_system.canalizaciones.infrastructure.adapater;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.canalizaciones.domain.dto.*;
import com.jeseg.admin_system.canalizaciones.domain.intreface.PersonaInterface;
import com.jeseg.admin_system.canalizaciones.infrastructure.entity.AccionCuidado;
import com.jeseg.admin_system.canalizaciones.infrastructure.entity.Persona;
import com.jeseg.admin_system.canalizaciones.infrastructure.repository.PersonaRepository;
import com.jeseg.admin_system.canalizaciones.infrastructure.repository.PersonaSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Repository
@AllArgsConstructor
public class PersonaAdapter implements PersonaInterface {
    private final PersonaRepository repository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CACHE_PREFIX = "personas:diarias:";

    @Override
    public List<PersonasResponse> sendPersonasCanalizacion(PersonaAskRequest filtros) {

        int pagina = (filtros.getPage() == null) ? 0 : Math.max(filtros.getPage(), 0);
        int tamano = (filtros.getTamano() == null) ? 30 : Math.max(filtros.getTamano(), 1);

        Pageable pageable = PageRequest.of(pagina, tamano);

        Specification<Persona> spec = PersonaSpecifications.filtrarPersonas(filtros);

        Page<Persona> resultadoPage = repository.findAll(spec, pageable);

        return resultadoPage.getContent().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void updateState(PersonaUpdateState request) {
        Persona persona = repository.findById(request.getId())
                .orElseThrow(BusinessException.Type.ERROR_PERSONA_NO_ENCONTRADA::build);

        persona.setEstado(request.getEstado());
        repository.save(persona);
    }

    @Override
    public long contarConFiltros(PersonaAskRequest filtros) {
        Specification<Persona> spec = PersonaSpecifications.filtrarPersonas(filtros);
        return repository.count(spec);
    }

    @Override
    @Transactional(value = "secondaryTransactionManager", readOnly = true)
    public List<PersonasResponse> obtenerTodoElDia(PersonaAskRequest filtros) {
        Specification<Persona> spec = PersonaSpecifications.filtrarPersonas(filtros);
        return repository.findAll(spec).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ContenedorCache obtenerDeCache(String llave) {
        String key = CACHE_PREFIX + llave;
        try {
            Object rawCache = redisTemplate.opsForValue().get(key);

            if (rawCache == null) {
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

            return mapper.convertValue(rawCache, ContenedorCache.class);

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void guardarEnCache(String llave, List<PersonasResponse> contenedor, LocalDateTime ultimaActualizacion) {
        if (llave == null || contenedor == null) {
            return;
        }

        String key = CACHE_PREFIX + llave;

        ContenedorCache nuevoContenedor = ContenedorCache.builder()
                .ultimaActualizacion(ultimaActualizacion != null ? ultimaActualizacion : LocalDateTime.now())
                .data(contenedor)
                .build();

        redisTemplate.opsForValue().set(key, nuevoContenedor);

        redisTemplate.expire(key, 24, java.util.concurrent.TimeUnit.HOURS);

    }

    @Override
    public List<PersonasResponse> obtenerNuevosDesde(LocalDateTime ultimoCheck, PersonaAskRequest filtros) {

        Specification<Persona> specFiltros = PersonaSpecifications.filtrarPersonas(filtros);

        Specification<Persona> specDelta = (root, query, cb) ->
                cb.greaterThan(root.get("fechaRegistro"), ultimoCheck);
        Specification<Persona> specFinal = specFiltros.and(specDelta);

        try (Stream<Persona> streamNuevos = repository.findAll(specFinal).stream()) {
            return streamNuevos
                    .map(this::mapToResponse)
                    .toList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }


    // Método auxiliar para la conversión
    private PersonasResponse mapToResponse(Persona persona) {
        return PersonasResponse.builder()
                .id(persona.getId())
                .primerNombre(persona.getPrimerNombre().toUpperCase())
                .segundoNombre(persona.getSegundoNombre() != null ? persona.getSegundoNombre().toUpperCase() : null)
                .primerApellido(persona.getPrimerApellido().toUpperCase())
                .segundoApellido(persona.getSegundoApellido() != null ? persona.getSegundoApellido().toUpperCase() : null)
                .tipoDocumento(persona.getTipoDocumento() != null ? persona.getTipoDocumento().toUpperCase() : null)
                .numeroDocumento(persona.getNumeroDoc().toUpperCase())
                .sexo(persona.getSexo() != null ? persona.getSexo().toUpperCase() : null)
                .fechaNacimiento(persona.getFechaNacimiento() != null ? persona.getFechaNacimiento().toString() : null)
                .canalizaciones(persona.getCanalizaciones() != null ? persona.getCanalizaciones().toUpperCase() : null)
                .deteccionTemprana(persona.getDeteccionTemprana() != null ? persona.getDeteccionTemprana().toUpperCase() : null)
                .rias(persona.getRias() != null ? persona.getRias().toUpperCase() : null)
                .urgencia(persona.getUrgencia() != null ? persona.getUrgencia().toUpperCase() : null)
                .regimen(persona.getRegimen() != null ? persona.getRegimen().toUpperCase() : null)
                .aseguradora(persona.getAseguradora() != null ? persona.getAseguradora().toUpperCase() : null)
                .nombreAcudiente(persona.getNombreAcudiente() != null ? persona.getNombreAcudiente().toUpperCase() : null)
                .telefonoAcudiente(persona.getTelefonoAcudiente() != null ? persona.getTelefonoAcudiente().toUpperCase() : null)
                .telefono(persona.getTelefono() != null ? persona.getTelefono().toUpperCase() : null)
                .estado(persona.getEstado() != null ? persona.getEstado().toUpperCase() : null)
                .ips(persona.getIps() != null ? persona.getIps().toUpperCase() : null)
                .aceptaFormulario(persona.getAceptaFormulario() != null ? persona.getAceptaFormulario().toUpperCase() : null)
                .observacionIps(persona.getObservacionIps() != null ? persona.getObservacionIps().toUpperCase() : null)
                .fechaRegistro(persona.getFechaRegistro() != null ? persona.getFechaRegistro() : null)
                .direccion(persona.getDireccion() != null ? persona.getDireccion().toUpperCase() : null)
                .barrio(persona.getBarrioVereda() != null ? persona.getBarrioVereda().toUpperCase() : null)
                .accionesCuidado(persona.getAcciones() != null ? persona.getAcciones().stream()
                        .map(this::mapToAccionCuidadoResponse)
                        .toList() : null)
                .build();
    }

    private AccionCuidadoResponse mapToAccionCuidadoResponse(AccionCuidado accion) {
        String responsableVisualizar = null;

        if (accion.getResponsableRelacion() != null && accion.getResponsableRelacion().getNombres() != null) {
            responsableVisualizar = accion.getResponsableRelacion().getNombres().toUpperCase();
        } else if (accion.getCedulaResponsableSeguimiento() != null) {
            responsableVisualizar = accion.getCedulaResponsableSeguimiento().toUpperCase();
        }

        return AccionCuidadoResponse.builder()
                .id(accion.getId())
                .estado(accion.getEstado() != null ? accion.getEstado().toUpperCase() : null)
                .fechaCompromiso(accion.getFechaSeguimiento() != null ? accion.getFechaCompromiso() : null)
                .situacionesPriorizadas(accion.getSituacionesPriorizadas() != null ? accion.getSituacionesPriorizadas().toUpperCase() : null)
                .responsableSeguimiento(responsableVisualizar)
                .build();
    }
}
