package com.jeseg.admin_system.canalizaciones.infrastructure.adapater;

import com.jeseg.admin_system.canalizaciones.domain.dto.AccionCuidadoRequest;
import com.jeseg.admin_system.canalizaciones.domain.intreface.AccionCuidadoInterface;
import com.jeseg.admin_system.canalizaciones.infrastructure.entity.AccionCuidado;
import com.jeseg.admin_system.canalizaciones.infrastructure.entity.Persona;
import com.jeseg.admin_system.canalizaciones.infrastructure.repository.AccionCuidadoRepository;
import com.jeseg.admin_system.canalizaciones.infrastructure.repository.PersonaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class AccionCuidadoAdapter implements AccionCuidadoInterface {
    private final PersonaRepository personaRepository;
    private final AccionCuidadoRepository accionCuidadoRepository;

    @Override
    @Transactional(value = "secondaryTransactionManager") // 🚨 Asegura la transacción de escritura en MochaHost
    public void createAccionCuidado(List<AccionCuidadoRequest> requests) {
        if (requests == null || requests.isEmpty()) return;

        // 1️⃣ OPTIMIZACIÓN DE RENDIMIENTO: Extraemos todos los documentos únicos del lote
        List<String> documentos = requests.stream()
                .map(AccionCuidadoRequest::getNumeroDoc)
                .filter(doc -> doc != null && !doc.isEmpty())
                .distinct()
                .toList();

        // Traemos todas las personas implicadas de un solo golpe (1 sola consulta SQL)
        Map<String, Persona> mapaPersonas = personaRepository.findByNumeroDocIn(documentos).stream()
                .collect(Collectors.toMap(Persona::getNumeroDoc, p -> p));

        // 2️⃣ PROCESAMOS EL MAPEO (DETECTANDO SI ES CREACIÓN O ACTUALIZACIÓN)
        List<AccionCuidado> accionesParaGuardar = requests.stream().map(r -> {
            if (r.getNumeroDoc() == null || r.getNumeroDoc().isEmpty()) {
                throw new IllegalArgumentException("El número de documento es obligatorio");
            }

            // Buscamos la persona en nuestro mapa en memoria
            Persona persona = mapaPersonas.get(r.getNumeroDoc());
            if (persona == null) {
                throw new IllegalArgumentException("No se encontró la persona con documento: " + r.getNumeroDoc());
            }

            // 🚨 AQUÍ OCURRE LA MAGIA DEL UPSERT:
            // Si el request trae un ID, intentamos buscar si ya existe en la base de datos para actualizarlo
            AccionCuidado accion;
            if (r.getId() != null) {
                // Si tiene ID, lo buscamos para modificarlo (puedes usar un findById del repositorio)
                accion = accionCuidadoRepository.findById(r.getId())
                        .orElse(new AccionCuidado()); // Si no existe por alguna razón, creamos uno nuevo
            } else {
                // Si no trae ID, es 100% una creación nueva
                accion = new AccionCuidado();
            }

            // Asignamos o actualizamos los valores de la entidad
            accion.setPersona(persona);
            accion.setSituacionesPriorizadas(r.getSituacionesPriorizadas());
            accion.setLogrosAlcanzados(r.getLogrosAlcanzados());
            accion.setResponsableFamilia(r.getResponsableFamilia());
            accion.setCedulaResponsableSeguimiento(r.getResponsableSeguimiento());
            accion.setFechaCompromiso(r.getFechaCompromiso());
            accion.setFechaSeguimiento(r.getFechaSeguimiento());
            accion.setEstado(r.getEstado());

            return accion;
        }).toList();

        // 3️⃣ GUARDADO MASIVO (Hibernate hará INSERT para los nuevos y UPDATE para los viejos)
        accionCuidadoRepository.saveAll(accionesParaGuardar);
    }

    private AccionCuidado mapToEntity(AccionCuidadoRequest r, Persona persona) {
        return AccionCuidado.builder()
                .persona(persona)
                .situacionesPriorizadas(r.getSituacionesPriorizadas())
                .logrosAlcanzados(r.getLogrosAlcanzados())
                .responsableFamilia(r.getResponsableFamilia())
                .cedulaResponsableSeguimiento( r.getResponsableSeguimiento())
                .fechaCompromiso(r.getFechaCompromiso())
                .fechaSeguimiento(r.getFechaSeguimiento())
                .estado(r.getEstado())
                .build();
    }
}
