package com.jeseg.admin_system.document.infrastructure.adapter;

import com.jeseg.admin_system.document.domain.dto.DocumentCreateRequest;
import com.jeseg.admin_system.document.domain.intreface.DocumentInterface;
import com.jeseg.admin_system.document.infrastructure.entity.DocumentEntity;
import com.jeseg.admin_system.document.infrastructure.repository.DocumentRepository;
import com.jeseg.admin_system.task.infrastructure.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class DocumentAdapter implements DocumentInterface {

    private final DocumentRepository roleRepository;
    private final TaskRepository taskRepository;

    @Override
    public void createDocument(DocumentCreateRequest documentCreateRequest) {
        roleRepository.save(DocumentEntity.builder()
                            .filename(documentCreateRequest.getFilename())
                            .url(documentCreateRequest.getUrl())
                            .task(taskRepository.getReferenceById(documentCreateRequest.getTask()))
                            .build());
    }

}
