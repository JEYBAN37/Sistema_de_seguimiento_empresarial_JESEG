package com.jeseg.admin_system.user.infrastructure.repository;

import com.jeseg.admin_system.user.infrastructure.entity.UserJepegEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserJepegEntity, Long> {
    List<UserJepegEntity> findByCompanyId(Long companyId);
}
