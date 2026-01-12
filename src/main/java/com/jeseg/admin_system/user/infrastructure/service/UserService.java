package com.jeseg.admin_system.user.infrastructure.service;


import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.usecase.UserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserUseCase usersUseCase;

    public void saveUsers(List<UserCreateRequest> users) {
        try {
            usersUseCase.createUsers(users);
        } catch (Exception e) {
            throw BusinessException.Type.ERROR_GUARDAR_USUARIOS.build(e);
        }
    }

}
