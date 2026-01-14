package com.jeseg.admin_system.approval.domain.dto;
import lombok.Data;

@Data
public class ApprovalCreateRequest {
    private Long task;
    private Long approver;
    private String status;
}
