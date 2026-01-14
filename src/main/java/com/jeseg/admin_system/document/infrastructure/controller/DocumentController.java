package com.jeseg.admin_system.document.infrastructure.controller;


import com.jeseg.admin_system.document.domain.dto.DocumentCreateRequest;
import com.jeseg.admin_system.document.infrastructure.service.DocumentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;

@RestController
@RequestMapping("/document")
@AllArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/create")
    public ResponseEntity<?> createDocument(@Valid @RequestBody DocumentCreateRequest companyCreateRequest) {
        documentService.saveDocument(companyCreateRequest);
        return ResponseEntity.ok(Collections.singletonMap("message", "Operación Exitosa"));
    }

}
