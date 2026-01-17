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

    public void UserDelegate(List<UserCreateRequest> usersToCreate ,List<UserCreateRequest> usersToUpload, List<UserCreateRequest> userToDelete) {

        if (userToDelete != null && !userToDelete.isEmpty())
            userInterface.deleteUsers(userToDelete);

        if (usersToUpload != null && !usersToUpload.isEmpty())
            userInterface.updateUsers(usersToUpload);

        if (usersToCreate != null && !usersToCreate.isEmpty())
            userInterface.createUsers(usersToCreate);
    }


}
