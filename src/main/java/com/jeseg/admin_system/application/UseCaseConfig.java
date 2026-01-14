package com.jeseg.admin_system.application;

import com.jeseg.admin_system.approval.domain.intreface.ApprovalInterface;
import com.jeseg.admin_system.approval.domain.usecase.ApprovalUseCases;
import com.jeseg.admin_system.company.domain.intreface.CompanyInterface;
import com.jeseg.admin_system.company.domain.usecase.CompanyUseCases;
import com.jeseg.admin_system.document.domain.intreface.DocumentInterface;
import com.jeseg.admin_system.document.domain.usecase.DocumentUseCases;
import com.jeseg.admin_system.hierarchyNode.domain.intreface.HierarchyNodeInterface;
import com.jeseg.admin_system.hierarchyNode.domain.usecase.HierarchyNodeUseCase;
import com.jeseg.admin_system.role.domain.intreface.RoleInterface;
import com.jeseg.admin_system.role.domain.usecase.RoleUseCases;
import com.jeseg.admin_system.task.domain.intreface.TaskInterface;
import com.jeseg.admin_system.task.domain.usecase.TaskUseCase;
import com.jeseg.admin_system.user.domain.intreface.UserInterface;
import com.jeseg.admin_system.user.domain.usecase.UserUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Value("${app.security.bcrypt.cost}")
    private int bcryptCost;


    @Bean
    public CompanyUseCases companyUseCases(CompanyInterface companyInterface) {
        return new CompanyUseCases(companyInterface);
    }

    @Bean
    public RoleUseCases roleUseCases (RoleInterface roleInterface){
        return new RoleUseCases(roleInterface);
    }

    @Bean
    public UserUseCase userUseCase (UserInterface userInterface){
        return new UserUseCase(userInterface);
    }

    @Bean
    public HierarchyNodeUseCase hierarchyNodeUseCase (HierarchyNodeInterface hierarchyNodeInterface){
        return new HierarchyNodeUseCase(hierarchyNodeInterface);
    }

    @Bean
    public TaskUseCase taskUseCase (TaskInterface taskInterface){
        return new TaskUseCase(taskInterface);
    }

    @Bean
    public DocumentUseCases documentUseCases (DocumentInterface documentInterface){
        return new DocumentUseCases(documentInterface);
    }

    @Bean
    public ApprovalUseCases approvalUseCases (ApprovalInterface approvalInterface){
        return new ApprovalUseCases(approvalInterface);
    }
}
