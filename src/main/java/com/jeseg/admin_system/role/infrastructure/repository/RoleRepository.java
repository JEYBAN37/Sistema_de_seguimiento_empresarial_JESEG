package com.jeseg.admin_system.role.infrastructure.repository;

import com.jeseg.admin_system.role.infrastructure.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    boolean existsByNameInAndCompanyId(List<String> names, Long companyId);
    List<RoleEntity> findAllByCompanyId( Long companyId);
}
