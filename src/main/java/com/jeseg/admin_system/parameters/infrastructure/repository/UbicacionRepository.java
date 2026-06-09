package com.jeseg.admin_system.parameters.infrastructure.repository;

import com.jeseg.admin_system.parameters.infrastructure.entity.UbicacionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UbicacionRepository extends JpaRepository<UbicacionesEntity, Long> {
    @Query("SELECT DISTINCT u FROM UbicacionesEntity u WHERE u.territorio IS NOT NULL AND u.estado = 'ACTIVO'")
    List<UbicacionesEntity> findDistinctTerritorios();
}
