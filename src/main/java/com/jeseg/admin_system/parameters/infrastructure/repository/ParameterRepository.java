package com.jeseg.admin_system.parameters.infrastructure.repository;

import com.jeseg.admin_system.parameters.infrastructure.entity.ParametersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterRepository extends JpaRepository<ParametersEntity, Long> {
    List<ParametersEntity> findByTipoIn(List<String> tipos);
}
