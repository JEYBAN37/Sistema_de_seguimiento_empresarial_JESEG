package com.jeseg.admin_system.task.domain.dto;

import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import com.jeseg.admin_system.user.infrastructure.entity.UserJepegEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class TaskCreateRequest {
    private String title;
    private String description;
    private String status;
    private List<Long> approvalRequired;
    private List<Long> assignedNodes;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate createdAt;
    private String priority;
    private String recurrenceType;
    private Long company;
    private Long createdBy;
}
