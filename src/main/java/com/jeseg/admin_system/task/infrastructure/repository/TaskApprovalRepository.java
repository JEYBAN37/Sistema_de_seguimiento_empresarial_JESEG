package com.jeseg.admin_system.task.infrastructure.repository;

import com.jeseg.admin_system.task.infrastructure.entity.TaskApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskApprovalRepository  extends JpaRepository<TaskApprovalEntity, Long> {
}
