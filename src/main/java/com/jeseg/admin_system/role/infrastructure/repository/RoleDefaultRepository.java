package com.jeseg.admin_system.role.infrastructure.repository;

import com.jeseg.admin_system.role.infrastructure.entity.Parameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDefaultRepository extends JpaRepository<Parameters,Long> {
}
