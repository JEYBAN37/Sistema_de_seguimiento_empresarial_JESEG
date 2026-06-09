package com.jeseg.admin_system.task.infrastructure.controller;

import com.jeseg.admin_system.company.domain.dto.CompanyCreateRequest;
import com.jeseg.admin_system.company.domain.dto.CompanyResponse;
import com.jeseg.admin_system.document.domain.dto.DocumentCreateRequest;
import com.jeseg.admin_system.document.domain.dto.DocumentResponse;
import com.jeseg.admin_system.task.domain.dto.*;
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

    @PostMapping(value = "/all", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskCheckResponse>> getAllTasks(@Valid @RequestBody TaskFilterRequest filtersRequest) {
        return ResponseEntity.ok(taskService.getTasksByUser(filtersRequest));
    }

    @PostMapping(value = "/anexos",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> loadTaskAndEvidences(@Valid @ModelAttribute DocumentCreateRequest documentCreateRequest) {
        return ResponseEntity.ok(taskService.loadTaskAndEvidences(documentCreateRequest));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseXdocument> getDocumentsByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.getTask(taskId));
    }

}
