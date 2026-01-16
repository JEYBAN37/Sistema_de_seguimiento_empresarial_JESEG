package com.jeseg.admin_system.user.domain.usecase;

import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.dto.UserCsvRow;
import com.jeseg.admin_system.user.domain.dto.UserResponse;
import com.jeseg.admin_system.user.domain.intreface.UserInterface;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
public class UserUseCase {
    private final UserInterface userInterface;
    public void createUsers(List<UserCreateRequest> users){
        userInterface.saveUsers(users);
    }

    public List<UserCsvRow> validUsers (MultipartFile file){
        return userInterface.validUserCsv(file);
    }

    public List<UserResponse> getUsersAdminPage(Long companyId) {
        return userInterface.getUsersAdminPage(companyId);
    }
}
