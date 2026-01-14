package com.jeseg.admin_system.approval.domain.intreface;
import com.jeseg.admin_system.approval.domain.dto.ApprovalCreateRequest;
import com.jeseg.admin_system.approval.domain.model.Approval;

import java.util.List;

public interface ApprovalInterface {
    void createApproval(ApprovalCreateRequest approvalCreateRequest );
}
