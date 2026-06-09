package com.jeseg.admin_system.canalizaciones.infrastructure.adapater;

import com.jeseg.admin_system.canalizaciones.domain.dto.PersonaAskRequest;
import com.jeseg.admin_system.canalizaciones.domain.dto.PersonasResponse;
import com.jeseg.admin_system.canalizaciones.domain.intreface.PersonaInterface;
import com.jeseg.admin_system.canalizaciones.infrastructure.entity.Persona;
import com.jeseg.admin_system.canalizaciones.infrastructure.repository.PersonaRepository;
import com.jeseg.admin_system.canalizaciones.infrastructure.repository.PersonaSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class PersonaAdapter implements PersonaInterface {
    private final PersonaRepository repository;

    @Override
    public List<PersonasResponse> sendPersonasCanalizacion(PersonaAskRequest filtros) {

        int pagina = (filtros.getPage() == null) ? 0 : Math.max(filtros.getPage(), 0);
        int tamano = (filtros.getTamano() == null) ? 30 : Math.max(filtros.getTamano(), 1);

        Pageable pageable = PageRequest.of(pagina, tamano);

        // Creamos la especificación con todos los filtros
        Specification<Persona> spec = PersonaSpecifications.filtrarPersonas(filtros);

        // Ejecutamos la consulta paginada
        Page<Persona> resultadoPage = repository.findAll(spec, pageable);

        // Mapeamos a la respuesta DTO
        return resultadoPage.getContent().stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Método auxiliar para la conversión
    private PersonasResponse mapToResponse(Persona persona) {
        return PersonasResponse.builder()
                .id(persona.getId())
                .primerNombre(persona.getPrimerNombre().toUpperCase())
                .segundoNombre(persona.getSegundoNombre() != null ? persona.getSegundoNombre().toUpperCase() : null)
                .primerApellido(persona.getPrimerApellido().toUpperCase())
                .segundoApellido(persona.getSegundoApellido() != null ? persona.getSegundoApellido().toUpperCase() : null)
                .tipoDocumento(persona.getTipoDocumento().toUpperCase())
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
                // Agrega aquí el resto de campos de tu imagen (sexo, regimen, etc.)
                .build();
    }
}
