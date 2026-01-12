package com.jeseg.admin_system.user.domain.usecase;

import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.intreface.UserInterface;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserUseCase {
    private final UserInterface userInterface;
    public void createUsers(List<UserCreateRequest> users){
        userInterface.saveUsers(users);
    }
}
