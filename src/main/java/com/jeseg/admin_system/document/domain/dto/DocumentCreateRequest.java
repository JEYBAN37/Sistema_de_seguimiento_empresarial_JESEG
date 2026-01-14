package com.jeseg.admin_system.document.domain.dto;
import lombok.Data;

@Data
public class DocumentCreateRequest {
    private String filename;
    private String mimetype;
    private String url;
    private Long task;
}
