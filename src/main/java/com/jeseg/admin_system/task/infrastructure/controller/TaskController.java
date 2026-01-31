package com.jeseg.admin_system.task.infrastructure.controller;

import com.jeseg.admin_system.company.domain.dto.CompanyCreateRequest;
import com.jeseg.admin_system.company.domain.dto.CompanyResponse;
import com.jeseg.admin_system.task.domain.dto.TaskCheckResponse;
import com.jeseg.admin_system.task.domain.dto.TaskCreateRequest;
import com.jeseg.admin_system.task.domain.dto.TaskFiltersRequest;
import com.jeseg.admin_system.task.domain.dto.TaskResponse;
import com.jeseg.admin_system.task.infrastructure.service.TaskService;
import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.infrastructure.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
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

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskCreateRequest taskCreateRequest) {
        return ResponseEntity.ok(taskService.saveTask(taskCreateRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskCheckResponse>> getAllTasks(TaskFiltersRequest filtersRequest) {
        List<TaskCheckResponse> tasks = taskService.getTasksByUser(filtersRequest);
        return ResponseEntity.ok(tasks);
    }
}
