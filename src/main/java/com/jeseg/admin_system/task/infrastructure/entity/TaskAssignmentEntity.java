package com.jeseg.admin_system.task.infrastructure.entity;
import com.jeseg.admin_system.hierarchyNode.infrastructure.entity.HierarchyNodeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task_assignments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskAssignmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference("task-assignments")
    private TaskEntity task;

    @ManyToOne
    private HierarchyNodeEntity empleado;

    private Boolean completed = false;
}
