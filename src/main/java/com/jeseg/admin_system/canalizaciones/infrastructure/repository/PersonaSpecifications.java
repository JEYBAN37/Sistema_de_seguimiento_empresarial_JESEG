package com.jeseg.admin_system.canalizaciones.infrastructure.repository;

import com.jeseg.admin_system.canalizaciones.domain.dto.PersonaAskRequest;
import com.jeseg.admin_system.canalizaciones.infrastructure.entity.Persona;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PersonaSpecifications {
    public static Specification<Persona> filtrarPersonas(PersonaAskRequest filtros) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtros.getAceptaFormulario() != null &&
                    !filtros.getAceptaFormulario().isEmpty() &&
                    filtros.getAceptaFormulario().equalsIgnoreCase("1")
            ) {
                predicates.add(cb.equal(root.get("aceptaFormulario"), "Si acepta"));
            }


            // 1. BUSCADOR GLOBAL (OR): Nombre o Documento
            if (filtros.getBusqueda() != null && !filtros.getBusqueda().isEmpty()) {
                String term = "%" + filtros.getBusqueda().toLowerCase() + "%";
                Predicate globalOr = cb.or(
                        cb.equal(root.get("numeroDoc"), filtros.getBusqueda()), // Exacto para doc
                        cb.like(cb.lower(root.get("primerNombre")), term),
                        cb.like(cb.lower(root.get("primerApellido")), term)
                );
                predicates.add(globalOr);
            }


            List<Predicate> orPreds = new ArrayList<>();

            if (filtros.getUrgencia() != null && !filtros.getUrgencia().isEmpty()
                    && filtros.getUrgencia().equalsIgnoreCase("1")) {
                orPreds.add(cb.notEqual(root.get("urgencia"), ""));
            }

            if (filtros.getDeteccionTemprana() != null && !filtros.getDeteccionTemprana().isEmpty()
                    && filtros.getDeteccionTemprana().equalsIgnoreCase("1")) {
                orPreds.add(cb.notEqual(root.get("deteccionTemprana"), ""));
            }

            if (filtros.getRias() != null && !filtros.getRias().isEmpty()
                    && filtros.getRias().equalsIgnoreCase("1")) {
                CriteriaBuilder.In<String> in = cb.in(root.get("rias"));
                in.value("");
                in.value("0.No |0");
                orPreds.add(cb.not(in));
            }

            if (!orPreds.isEmpty()) {
                predicates.add(cb.or(orPreds.toArray(new Predicate[0])));
            }


            // 2. FILTRO INDEPENDIENTE (AND): Aseguradora
            //if (filtros.getCanalizacion_id() != null && !filtros.getCanalizacion_id().isEmpty()) {
            //   predicates.add(cb.equal(root.get("aseguradora"), filtros.getCanalizacion_id()));
            //}

            // 3. FILTRO INDEPENDIENTE (AND): Canalización ID
            //if (filtros.getCanalizacionId() != null) {
            //    predicates.add(cb.equal(root.get("canalizacion_id"), filtros.getCanalizacionId()));
            //}

            // 4. ESPACIO PARA MÁS FILTROS (Zonas, Discapacidad, etc.)
            // if (filtros.getZona() != null) ...

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
