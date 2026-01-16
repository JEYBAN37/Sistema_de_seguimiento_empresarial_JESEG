package com.jeseg.admin_system.user.domain.intreface;

import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.dto.UserCsvRow;
import com.jeseg.admin_system.user.domain.dto.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserInterface {
    void saveUsers(List<UserCreateRequest> users);
    List<UserResponse> getUsersAdminPage(Long companyId);
    List <UserCsvRow> validUserCsv(MultipartFile file);
}
