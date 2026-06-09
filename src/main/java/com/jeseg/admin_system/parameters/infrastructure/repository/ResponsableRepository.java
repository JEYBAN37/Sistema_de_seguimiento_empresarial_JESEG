package com.jeseg.admin_system.parameters.infrastructure.repository;

import com.jeseg.admin_system.parameters.infrastructure.entity.ResponsableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResponsableRepository extends JpaRepository<ResponsableEntity, Long> {
    @Query("SELECT r FROM ResponsableEntity r WHERE r.contrato = 'ACTIVO' AND r.profesion IN ('ENFERMERA', 'MEDICO', 'PSICOLOGO')")
    List<ResponsableEntity> findResponsables();

    Optional<ResponsableEntity> findNombreById(Long id);
}
