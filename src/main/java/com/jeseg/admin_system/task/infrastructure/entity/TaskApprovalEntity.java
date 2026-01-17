package com.jeseg.admin_system.task.infrastructure.entity;

import com.jeseg.admin_system.hierarchyNode.infrastructure.entity.HierarchyNodeEntity;
import com.jeseg.admin_system.task.domain.dto.ApprovalStatus;
import jakarta.persistence.*;
import org.springframework.scheduling.config.Task;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_approvals")
public class TaskApprovalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Task task;

    @ManyToOne
    private HierarchyNodeEntity approver;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    private Integer approvalOrder; // orden jerárquico

    private LocalDateTime actionDate;

    private String comments;
}
