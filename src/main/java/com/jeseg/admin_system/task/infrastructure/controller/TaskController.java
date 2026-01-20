package com.jeseg.admin_system.task.infrastructure.controller;

import com.jeseg.admin_system.company.domain.dto.CompanyCreateRequest;
import com.jeseg.admin_system.company.domain.dto.CompanyResponse;
import com.jeseg.admin_system.task.domain.dto.TaskCreateRequest;
import com.jeseg.admin_system.task.infrastructure.service.TaskService;
import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.infrastructure.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUsers(@Valid @ModelAttribute TaskCreateRequest taskCreateRequest) {
        taskService.saveTask(taskCreateRequest);
        return ResponseEntity.ok(Collections.singletonMap("message", "Operación Exitosa"));
    }
}
