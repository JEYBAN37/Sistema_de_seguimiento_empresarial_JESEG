package com.jeseg.admin_system.document.infrastructure.adapter;

import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.document.domain.dto.DocumentCreateRequest;
import com.jeseg.admin_system.document.domain.intreface.DocumentInterface;
import com.jeseg.admin_system.document.infrastructure.entity.DocumentEntity;
import com.jeseg.admin_system.document.infrastructure.repository.DocumentRepository;
import com.jeseg.admin_system.task.infrastructure.entity.TaskEntity;
import com.jeseg.admin_system.task.infrastructure.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.jeseg.admin_system.application.UploadsGeneric.uploadedFiles;

@Repository
@AllArgsConstructor
public class DocumentAdapter implements DocumentInterface {

    private final DocumentRepository roleRepository;
    private final TaskRepository taskRepository;

    @Override
    public void createDocument(DocumentCreateRequest documentCreateRequest) {
        roleRepository.save(DocumentEntity.builder().build());
    }

    @Override
    public void loadAnexos(DocumentCreateRequest documentCreateRequest) {
        TaskEntity taskId = taskRepository.findById(documentCreateRequest.getTaskId())
                .orElseThrow(BusinessException.Type.ERROR_GUARDAR_HIERARCHY_COMPANIA_NO_EXISTE::build);

        List<MultipartFile> anexos = documentCreateRequest.getFiles();

        List<DocumentEntity> documentos = anexos.stream().map(anexo ->
                 DocumentEntity.builder()
                        .task(taskRepository.findById(taskId.getId()).orElseThrow())
                        .url(uploadedFiles(anexo,"uploads/anexos/"))
                        .typeAttachment(anexo.getContentType())
                        .filename(anexo.getOriginalFilename())
                        .build()
        ).toList();

        roleRepository.saveAll(documentos);
    }

}
