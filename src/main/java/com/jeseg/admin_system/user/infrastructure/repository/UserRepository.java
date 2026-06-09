package com.jeseg.admin_system.user.infrastructure.repository;

import com.jeseg.admin_system.user.infrastructure.entity.UserJepegEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserJepegEntity, Long> {
    List<UserJepegEntity> findByCompanyId(Long companyId);

    @Query("SELECT u FROM UserJepegEntity u WHERE u.contrato IN :contratos AND u.company.id = :companyId")
    Optional<List<UserJepegEntity>> findByContratoInAndCompanyId(
            @Param("contratos") List<String> contratos,
            @Param("companyId") Long companyId
    );
}
