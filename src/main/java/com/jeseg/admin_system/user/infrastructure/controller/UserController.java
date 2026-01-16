package com.jeseg.admin_system.user.infrastructure.controller;

import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.dto.UserPageAdminRequest;
import com.jeseg.admin_system.user.infrastructure.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUsers(@Valid @RequestBody List<UserCreateRequest> usersCreateRequest) {
        userService.saveUsers(usersCreateRequest);
        return ResponseEntity.ok(Collections.singletonMap("message", "Operación Exitosa"));
    }

    @GetMapping("/parameters/{companyId}")
    public ResponseEntity<UserPageAdminRequest> getCompany (@PathVariable Long companyId) {
        return ResponseEntity.ok(userService.getUsersAdminPage(companyId));
    }

    @PostMapping(value = "/upload-csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadUsersCsv(@Valid @ModelAttribute MultipartFile fileContent) {
        userService.uploadUsersFromCsv(fileContent);
        System.out.println("Archivo procesado correctamente");
        return ResponseEntity.ok(Collections.singletonMap("message", "Operación Exitosa"));
    }


}
