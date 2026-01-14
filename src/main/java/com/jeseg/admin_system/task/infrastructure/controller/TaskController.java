package com.jeseg.admin_system.task.infrastructure.controller;

import com.jeseg.admin_system.task.domain.dto.TaskCreateRequest;
import com.jeseg.admin_system.task.infrastructure.service.TaskService;
import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.infrastructure.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> createUsers(@Valid @RequestBody TaskCreateRequest taskCreateRequest) {
        taskService.saveTask(taskCreateRequest);
        return ResponseEntity.ok(Collections.singletonMap("message", "Operación Exitosa"));
    }

}
