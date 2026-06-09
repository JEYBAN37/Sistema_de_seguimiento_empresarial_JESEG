package com.jeseg.admin_system.approval.infrastructure.adapter;

import com.jeseg.admin_system.approval.domain.dto.ApprovalCreateRequest;
import com.jeseg.admin_system.approval.domain.intreface.ApprovalInterface;
import com.jeseg.admin_system.approval.infrastructure.entity.ApprovalEntity;
import com.jeseg.admin_system.approval.infrastructure.repository.ApprovalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ApprovalAdapter implements ApprovalInterface {

    private final ApprovalRepository approvalInterface;

    @Override
    public void createApproval(ApprovalCreateRequest approvalCreateRequest) {
        approvalInterface.save(ApprovalEntity.builder()
                            .build());
    }

}
