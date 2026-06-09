package com.jeseg.admin_system.task.infrastructure.repository;

import com.jeseg.admin_system.task.domain.dto.TaskFilterRequest;
import com.jeseg.admin_system.task.infrastructure.entity.TaskEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.jeseg.admin_system.task.domain.model.Convert.convertTime;

public class TaskSpecifications {

    public static Specification<TaskEntity> filtrarTareas(TaskFilterRequest filters) {
       return (root, query, cb) -> {
           query.distinct(true);
           List<Predicate> predicates = new ArrayList<>();

           // Filtro por Título (Parcial: LIKE)
           if (filters.getTerritorio() != null && !filters.getTerritorio().isEmpty())
           {
               predicates.add(cb.like(cb.lower(root.get("territorio")), "%" + filters.getTerritorio() + "%"));
           }

           if (filters.getFechaBusqueda() != null) {

               LocalDateTime fechaBusqueda = convertTime(filters.getFechaBusqueda());
               LocalDate fechaBusquedaDate = fechaBusqueda.toLocalDate();

               LocalDateTime fechaInicio = fechaBusquedaDate.atStartOfDay();
               LocalDateTime fechaFin = fechaBusquedaDate.atTime(23, 59, 59);

               predicates.add(cb.between(root.get("startDate"), fechaInicio, fechaFin));
           }

           if (filters.getStartDate() != null) {
               LocalDateTime startDate = convertTime(filters.getStartDate());
               predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), startDate));
           }

           if (filters.getEndDate() != null) {
               LocalDateTime endDate = convertTime(filters.getEndDate());
               predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), endDate));
           }

           if (filters.getNoDone() != null && filters.getNoDone()) {
               predicates.add(cb.notEqual(root.get("status"), "TERMINADA"));
           }

           return cb.and(predicates.toArray(new Predicate[0]));

       };
    }
}
