package com.jeseg.admin_system.approval.infrastructure.service;


import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.approval.domain.dto.ApprovalCreateRequest;
import com.jeseg.admin_system.approval.domain.usecase.ApprovalUseCases;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ApprovalService {
    private final ApprovalUseCases approvalUseCases;

    public void saveApproval(ApprovalCreateRequest approvalCreateRequest) {
        try {
            approvalUseCases.createApproval(approvalCreateRequest);
        } catch (Exception e) {
            throw BusinessException.Type.ERROR_GUARDAR_APPROVAL.build();
        }
    }
}
