package com.jeseg.admin_system.document.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentResponse {
    private String url;
    private String timeSregistered;
    private String location;
}
