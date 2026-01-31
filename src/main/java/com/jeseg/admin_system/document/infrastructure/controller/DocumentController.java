package com.jeseg.admin_system.document.infrastructure.controller;


import com.jeseg.admin_system.document.domain.dto.DocumentCreateRequest;
import com.jeseg.admin_system.document.infrastructure.service.DocumentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping(value = "/anexos",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAnexos(@Valid @ModelAttribute DocumentCreateRequest documentCreateRequest) {
        documentService.saveAnexos(documentCreateRequest);
        return ResponseEntity.ok(Collections.singletonMap("message", "Operación Exitosa"));
    }

}
