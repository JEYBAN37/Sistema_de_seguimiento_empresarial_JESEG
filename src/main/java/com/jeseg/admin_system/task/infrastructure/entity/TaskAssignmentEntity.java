package com.jeseg.admin_system.task.infrastructure.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import com.jeseg.admin_system.user.infrastructure.entity.UserJepegEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task_assignments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskAssignmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TaskEntity task;

    @ManyToOne
    private UserJepegEntity user;

    private Boolean completed = false;
}
