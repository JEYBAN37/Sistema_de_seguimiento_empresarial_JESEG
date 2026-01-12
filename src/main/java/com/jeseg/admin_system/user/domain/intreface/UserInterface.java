package com.jeseg.admin_system.user.domain.intreface;

import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import java.util.List;

public interface UserInterface {
    void saveUsers(List<UserCreateRequest> users);
}
