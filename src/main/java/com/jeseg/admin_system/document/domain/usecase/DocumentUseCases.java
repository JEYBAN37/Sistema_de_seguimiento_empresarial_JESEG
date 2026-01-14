package com.jeseg.admin_system.document.domain.usecase;

import com.jeseg.admin_system.document.domain.dto.DocumentCreateRequest;
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
}
