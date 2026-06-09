package com.jeseg.admin_system.hierarchyNode.infrastructure.controller;

import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeCreateRequest;
import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeResponse;
import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeTaskRequest;
import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyNodeTaskResponse;
import com.jeseg.admin_system.hierarchyNode.infrastructure.service.HierarchyNodeService;
import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.infrastructure.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/hierarchy")
@AllArgsConstructor
public class HierarchyNodeController {
    private final HierarchyNodeService hierarchyNodeService;

    @PostMapping("/create")
    public ResponseEntity<?> createHierarchy(@Valid @RequestBody HierarchyNodeCreateRequest hierarchyNodeCreateRequest) {
        hierarchyNodeService.saveHierachy(hierarchyNodeCreateRequest);
        return ResponseEntity.ok(Collections.singletonMap("message", "Operación Exitosa"));
    }

    @GetMapping("/all/{companyId}")
    public ResponseEntity<List<HierarchyNodeResponse>> getStructure(@PathVariable Long companyId) {
        List<HierarchyNodeResponse> hierarchies = hierarchyNodeService.getStructureHierachy(companyId);
        return ResponseEntity.ok(hierarchies);
    }

    @GetMapping("/nodes/user/{request}")
    public ResponseEntity<HierarchyNodeTaskResponse> getFlatStructure(@PathVariable Long request) {
        HierarchyNodeTaskResponse hierarchies = hierarchyNodeService.getFlatStructure(request);
        return ResponseEntity.ok(hierarchies);
    }
}
