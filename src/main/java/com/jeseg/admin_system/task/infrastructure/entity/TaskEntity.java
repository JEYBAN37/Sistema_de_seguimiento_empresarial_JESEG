package com.jeseg.admin_system.task.infrastructure.entity;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import com.jeseg.admin_system.hierarchyNode.infrastructure.entity.HierarchyNodeEntity;
import com.jeseg.admin_system.task.domain.dto.TaskPriority;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "El titulo es obligatorio")
    private String title;

    @Column(length = 500, nullable = false)
    @NotBlank(message = "descripcion es obligatoria")
    private String description;


    @Column(length = 20)
    private String status; // PENDING, IN_PROGRESS, DONE

    private Boolean approvalRequired = false;

    @ManyToOne
    private CompanyEntity company;

    @ManyToOne
    private HierarchyNodeEntity createdBy;

    @Column(name = "created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(name = "start_date", columnDefinition = "DATETIME")
    private LocalDateTime startDate;

    @Column(name = "end_date", columnDefinition = "DATETIME")
    private LocalDateTime endDate;

    private String placeOrLocation;

    @JsonManagedReference("task-assignments")
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TaskAssignmentEntity> assignments; // Cambia List por Set

    @JsonManagedReference("task-approvals")
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TaskApprovalEntity> approvals; // Cambia List por Set

    // Relationship to task schedules (mappedBy = "task" in TaskScheduleEntity)
    @JsonManagedReference("task-schedules")
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TaskScheduleEntity> taskSchedules;
}
