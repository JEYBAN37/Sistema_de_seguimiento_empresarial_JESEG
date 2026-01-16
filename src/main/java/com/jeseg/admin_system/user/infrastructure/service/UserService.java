package com.jeseg.admin_system.user.infrastructure.service;



import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeResponse;
import com.jeseg.admin_system.hierarchyNode.domain.usecase.HierarchyNodeUseCase;
import com.jeseg.admin_system.parameters.domain.usecases.ParametersUseCase;
import com.jeseg.admin_system.role.domain.dto.RoleResponse;
import com.jeseg.admin_system.role.domain.usecase.RoleUseCases;
import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.dto.UserPageAdminRequest;
import com.jeseg.admin_system.user.domain.dto.UserResponse;
import com.jeseg.admin_system.user.domain.usecase.UserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserUseCase usersUseCase;
    private final HierarchyNodeUseCase hierarchyNodeUseCase;
    private final RoleUseCases roleUseCases;
    private final ParametersUseCase parametersUseCase;


    public UserPageAdminRequest getUsersAdminPage(Long companyId) {
        List<HierarchyNodeResponse> jearaquias =  hierarchyNodeUseCase.getStructure(companyId);
        List<RoleResponse> roles_listados =  roleUseCases.getRoleXCompanies(companyId);
        List<UserResponse> users = usersUseCase.getUsersAdminPage(companyId);
        List<String> funcionalidades = parametersUseCase.parameterById(companyId);

        return UserPageAdminRequest.builder()
                .hierarchies(jearaquias)
                .roles(roles_listados)
                .users(users)
                .functionsAvailable(funcionalidades)
                .build();

    }

    public void uploadUsersFromCsv(MultipartFile csvContent) {
        List<List<UserCreateRequest>> users =  usersUseCase.validUsers(csvContent);

        List<UserCreateRequest> invalidUsers = users.get(0);
        if (!invalidUsers.isEmpty()){
            return;
        }
        usersUseCase.UserDelegate(users.get(1),users.get(2),users.get(3));
    }



}
