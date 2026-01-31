package com.jeseg.admin_system.document.domain.dto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class DocumentCreateRequest {
    private List<MultipartFile> files;
    private Long taskId;
}
