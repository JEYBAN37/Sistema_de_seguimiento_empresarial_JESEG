package com.jeseg.admin_system.task.infrastructure.entity;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import com.jeseg.admin_system.task.domain.dto.TaskPriority;
import com.jeseg.admin_system.user.infrastructure.entity.UserJepegEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Data
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

    @Column(length = 255, nullable = false)
    @NotBlank(message = "descripcion es obligatoria")
    private String description;


    @Column(length = 20)
    private String status; // PENDING, IN_PROGRESS, DONE

    private Boolean approvalRequired = false;

    @ManyToOne
    private CompanyEntity company;

    @ManyToOne
    private UserJepegEntity createdBy;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;


    private LocalDate startDate;
    private LocalDate endDate;

}
