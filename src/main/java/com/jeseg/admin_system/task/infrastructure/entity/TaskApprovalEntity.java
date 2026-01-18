package com.jeseg.admin_system.task.infrastructure.entity;

import com.jeseg.admin_system.hierarchyNode.infrastructure.entity.HierarchyNodeEntity;
import com.jeseg.admin_system.task.domain.dto.ApprovalStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.scheduling.config.Task;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_approvals")
@Data
public class TaskApprovalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TaskEntity task;

    @ManyToOne
    private HierarchyNodeEntity approver;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    private Integer approvalOrder;

    private LocalDateTime actionDate;

    @Column(length = 255)
    private String comments;
}
