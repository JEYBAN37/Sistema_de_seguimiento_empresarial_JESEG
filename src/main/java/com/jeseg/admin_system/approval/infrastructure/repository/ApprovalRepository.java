package com.jeseg.admin_system.approval.infrastructure.repository;

import com.jeseg.admin_system.approval.infrastructure.entity.ApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {
}
