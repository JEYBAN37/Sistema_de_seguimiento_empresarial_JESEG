package com.jeseg.admin_system.document.domain.intreface;
import com.jeseg.admin_system.document.domain.dto.DocumentCreateRequest;
import com.jeseg.admin_system.document.domain.model.Document;

import java.util.List;

public interface DocumentInterface {
    void createDocument(DocumentCreateRequest documentCreateRequest );
    void loadAnexos (DocumentCreateRequest documentCreateRequest);
}
