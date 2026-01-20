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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor // Genera constructor con todos los campos
@NoArgsConstructor
public class TaskCreateRequest {

    private String title;
    private String description;
    private String status;
    private List<Long> approvalRequired;
    private List<Long> assignedNodes;
    private List<MultipartFile> anexos;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate createdAt;
    private String priority;
    private String recurrenceType;
    private Long company;
    private Long createdBy;
}
