package com.jeseg.admin_system.task.infrastructure.entity;

import com.jeseg.admin_system.hierarchyNode.infrastructure.entity.HierarchyNodeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks_comments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(length = 5000, nullable = false)
    @NotBlank(message = "mensaje es obligatoria")
    private String comment;

    @Column(name = "created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @ManyToOne
    private HierarchyNodeEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskEntity task;
}
