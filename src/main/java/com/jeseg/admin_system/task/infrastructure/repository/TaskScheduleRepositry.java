package com.jeseg.admin_system.task.infrastructure.repository;

import com.jeseg.admin_system.task.infrastructure.entity.TaskScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskScheduleRepositry extends JpaRepository<TaskScheduleEntity, Long> {
}
