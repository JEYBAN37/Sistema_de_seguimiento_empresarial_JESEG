package com.jeseg.admin_system.user.domain.usecase;

import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.dto.UserResponse;
import com.jeseg.admin_system.user.domain.intreface.UserInterface;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
public class UserUseCase {
    private final UserInterface userInterface;

    public List<List<UserCreateRequest>> validUsers (MultipartFile file){
        return userInterface.validUserCsv(file);
    }

    public List<UserResponse> getUsersAdminPage(Long companyId) {
        return userInterface.getUsersAdminPage(companyId);
    }

    public void UserDelegate(List<UserCreateRequest> usersToCreate ,List<UserCreateRequest> usersToUpload, List<UserCreateRequest> userToDelete){ {
        userInterface.deleteUsers(userToDelete.stream().map(UserCreateRequest::getContrato).toList(),userToDelete.get(0).getCompany());}
        userInterface.updateUsers(usersToUpload);
        userInterface.createUsers(usersToCreate);
    }


}
