package com.jeseg.admin_system.document.infrastructure.repository;

import com.jeseg.admin_system.document.infrastructure.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

    @Query("SELECT d FROM DocumentEntity d WHERE d.task.id = :taskId")
    List<DocumentEntity> findByTask(Long taskId);
}
