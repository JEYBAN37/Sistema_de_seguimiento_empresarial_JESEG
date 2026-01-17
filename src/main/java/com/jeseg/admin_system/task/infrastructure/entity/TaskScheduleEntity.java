package com.jeseg.admin_system.task.infrastructure.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "task_schedules")
public class TaskScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TaskEntity task;

    @Enumerated(EnumType.STRING)
    private RecurrenceType recurrenceType;
    // DAILY, WEEKLY, MONTHLY

    private String recurrenceConfig;
    // JSON: días del mes, semanas, etc.

    private LocalDate startDate;
    private LocalDate endDate;
}
