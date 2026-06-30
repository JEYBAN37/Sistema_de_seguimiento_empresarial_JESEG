package com.jeseg.admin_system.canalizaciones.infrastructure.repository;

import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.canalizaciones.domain.dto.PersonaAskRequest;
import com.jeseg.admin_system.canalizaciones.infrastructure.entity.Persona;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

            // 2. Filtro IPS
            if (filtros.getIps() != null && !filtros.getIps().isEmpty()) {
                CriteriaBuilder.In<String> in = cb.in(root.get("ips"));
                for (String ips : filtros.getIps()) {
                    in.value(ips);
                }
                predicates.add(in);
            } else {
                throw BusinessException.Type.ERROR_IPS_NO_VACIAS.build();
            }

            // 3. Periodo de búsqueda
            if (filtros.getPeriodoBusquedaBetween() != null && !filtros.getPeriodoBusquedaBetween().isEmpty()) {
                try {
                    String[] fechas = filtros.getPeriodoBusquedaBetween().split(",");
                    if (fechas.length != 2) {
                        throw BusinessException.Type.ERROR_FORMATO_PERIODO_BUSQUEDA_INVALIDO.build();
                    }

                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String fechaInicioStr = fechas[0].trim();
                    String fechaFinStr = fechas[1].trim();

                    LocalDate.parse(fechaInicioStr, fmt);
                    LocalDate.parse(fechaFinStr, fmt);
                    predicates.add(cb.between(root.get("fechaRegistro"), fechaInicioStr, fechaFinStr));

                } catch (Exception e) {
                    System.out.println("Error al parsear el periodo de búsqueda: " + e.getMessage());
                    throw BusinessException.Type.FECHA_OBLIGATORIA_CONSULTANTE.build();
                }

            }

            if (filtros.getPeriodoBusqueda() != null && !filtros.getPeriodoBusqueda().isEmpty()) {
                try {
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate fecha = LocalDate.parse(filtros.getPeriodoBusqueda(), fmt);

                    predicates.add(cb.between(root.get("fechaRegistro"), fecha.atStartOfDay(), fecha.atTime(23, 59, 59)));
                } catch (Exception e) {
                    throw BusinessException.Type.FECHA_OBLIGATORIA_CONSULTANTE.build();
                }
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
