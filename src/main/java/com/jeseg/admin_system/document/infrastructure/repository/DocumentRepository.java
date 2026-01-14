package com.jeseg.admin_system.document.infrastructure.repository;

import com.jeseg.admin_system.document.infrastructure.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
}
