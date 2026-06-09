package com.jeseg.admin_system.task.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
public class TaskAndDocument {
    private Long id;
    private List<MultipartFile> files;
}
