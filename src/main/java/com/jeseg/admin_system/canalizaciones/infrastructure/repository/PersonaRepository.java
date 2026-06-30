package com.jeseg.admin_system.canalizaciones.infrastructure.repository;

import com.jeseg.admin_system.canalizaciones.infrastructure.entity.Persona;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> , JpaSpecificationExecutor<Persona> {
    @Query("SELECT COUNT(p) FROM Persona p WHERE p.fechaRegistro BETWEEN :fechaInicio AND :fechaFin")
    long countByFechaRegistro(@Param("fechaInicio")  LocalDateTime fechaInicio , @Param("fechaFin") LocalDateTime fechaFin);
    @Override
    @EntityGraph(attributePaths = {"acciones", "acciones.responsableRelacion"})
    List<Persona> findAll(Specification<Persona> spec);

    Optional<Persona>findByNumeroDocIn(List<String> numeroDoc);
}
