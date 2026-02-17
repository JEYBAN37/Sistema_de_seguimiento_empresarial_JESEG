package com.jeseg.admin_system.task.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jeseg.admin_system.task.domain.dto.RecurrenceType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "task_schedules")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference("task-schedules")
    private TaskEntity task;

    @Enumerated(EnumType.STRING)
    private RecurrenceType recurrenceType;
    // DAILY, WEEKLY, MONTHLY

    private LocalDate startDate;
    private LocalDate endDate;
}
