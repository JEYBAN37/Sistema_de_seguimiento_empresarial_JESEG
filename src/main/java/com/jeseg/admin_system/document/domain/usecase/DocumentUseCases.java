package com.jeseg.admin_system.document.domain.usecase;

import com.jeseg.admin_system.document.domain.dto.DocumentCreateRequest;
import com.jeseg.admin_system.document.domain.dto.DocumentResponse;
import com.jeseg.admin_system.document.domain.intreface.DocumentInterface;
import com.jeseg.admin_system.document.domain.model.Document;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class DocumentUseCases {

    private final DocumentInterface documentInterface;

    public void createDocument(DocumentCreateRequest roleCreateRequest) {
        documentInterface.createDocument(roleCreateRequest);
    }

    public void createAnexos(DocumentCreateRequest documentCreateRequest) {
       documentInterface.loadAnexos(documentCreateRequest);
    }

    public List<DocumentResponse> getDocumentsByTaskId(Long taskId) {
          return documentInterface.getDocumentsByTaskId(taskId);
    }
}
