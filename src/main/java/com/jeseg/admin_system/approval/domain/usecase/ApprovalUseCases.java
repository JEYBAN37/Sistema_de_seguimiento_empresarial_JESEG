package com.jeseg.admin_system.approval.domain.usecase;

import com.jeseg.admin_system.approval.domain.dto.ApprovalCreateRequest;
import com.jeseg.admin_system.approval.domain.intreface.ApprovalInterface;
import com.jeseg.admin_system.approval.domain.model.Approval;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ApprovalUseCases {

    private final ApprovalInterface approvalInterface;

    public void createApproval(ApprovalCreateRequest approvalCreateRequest) {
            approvalInterface.createApproval(approvalCreateRequest);
    }

}
