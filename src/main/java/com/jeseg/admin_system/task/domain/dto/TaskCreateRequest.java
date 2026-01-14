package com.jeseg.admin_system.task.domain.dto;

import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import com.jeseg.admin_system.user.infrastructure.entity.UserJepegEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor // Genera constructor con todos los campos
@NoArgsConstructor
public class TaskCreateRequest {

    private String title;
    private String description;
    private String status;
    private Boolean approvalRequired;
    private Long company;
    private Long createdBy;
}
