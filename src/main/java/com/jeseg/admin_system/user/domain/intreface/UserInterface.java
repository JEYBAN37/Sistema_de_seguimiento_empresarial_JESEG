package com.jeseg.admin_system.user.domain.intreface;

import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.dto.UserCsvRow;
import com.jeseg.admin_system.user.domain.dto.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserInterface {
    List<UserResponse> getUsersAdminPage(Long companyId);
    List<List<UserCreateRequest>> validUserCsv(MultipartFile file);
    void createUsers(List<UserCreateRequest> users);
    void updateUsers(List<UserCreateRequest> users);
    void deleteUsers(List<String> userContrats, Long companyId);
}
