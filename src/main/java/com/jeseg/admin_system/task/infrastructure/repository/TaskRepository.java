package com.jeseg.admin_system.task.infrastructure.repository;

import com.jeseg.admin_system.task.infrastructure.entity.TaskEntity;
import com.jeseg.admin_system.user.infrastructure.entity.UserJepegEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> , JpaSpecificationExecutor<TaskEntity> {
    @Override
    @EntityGraph(attributePaths = {"assignments.empleado", "approvals.approver"})
    List<TaskEntity> findAll(Specification<TaskEntity> spec);
}
