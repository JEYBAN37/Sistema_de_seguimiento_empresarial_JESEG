package com.jeseg.admin_system.document.infrastructure.service;


import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.document.domain.dto.DocumentCreateRequest;
import com.jeseg.admin_system.document.domain.dto.DocumentResponse;
import com.jeseg.admin_system.document.domain.model.Document;
import com.jeseg.admin_system.document.domain.usecase.DocumentUseCases;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DocumentService {
    private final DocumentUseCases documentUseCases;

    public void saveDocument(DocumentCreateRequest companyCreateRequest) {
        try {
            documentUseCases.createDocument(companyCreateRequest);
        } catch (Exception e) {
            throw BusinessException.Type.ERROR_GUARDAR_ROLE.build();
        }
    }

    public void saveAnexos (DocumentCreateRequest documentCreateRequest) {
            documentUseCases.createAnexos(documentCreateRequest);
    }

    public List<DocumentResponse> getDocumentsByTaskId(Long taskId) {
        return documentUseCases.getDocumentsByTaskId(taskId);
    }

}
