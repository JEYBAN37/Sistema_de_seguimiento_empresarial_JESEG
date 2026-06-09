package com.jeseg.admin_system.task.domain.dto;

import lombok.*;

@Data
public class TaskFilterRequest {
    private String startDate;
    private String endDate;
    private String fechaBusqueda;
    private String territorio;
    private Integer page;
    private Integer tamano;
    private Boolean noDone;
    
}
