package com.jeseg.admin_system.task.infrastructure.entity;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import com.jeseg.admin_system.hierarchyNode.infrastructure.entity.HierarchyNodeEntity;
import com.jeseg.admin_system.task.domain.dto.TaskPriority;
import com.jeseg.admin_system.user.infrastructure.entity.UserJepegEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
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

    private LocalDate createdAt;

    private LocalDate updatedAt;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TaskAssignmentEntity> assignments; // Cambia List por Set

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TaskApprovalEntity> approvals; // Cambia List por Set
}
