package com.jeseg.admin_system.task.infrastructure.repository;

import com.jeseg.admin_system.task.infrastructure.entity.TaskAssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignmentEntity, Long> {}
