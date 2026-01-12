package com.jeseg.admin_system.user.infrastructure.controller;

import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.infrastructure.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping("/")
//    public ResponseEntity<List<HierarchyNodeEntity>> getCompany (){
//        return ResponseEntity.ok(companyService.allCompanies());
//    }
}
