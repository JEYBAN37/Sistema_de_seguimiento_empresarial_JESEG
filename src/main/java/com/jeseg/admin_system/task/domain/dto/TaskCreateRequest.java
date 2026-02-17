package com.jeseg.admin_system.task.domain.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
public class TaskCreateRequest {
    private String title;
    private List<Integer> start;
    private List<Integer> end;
    private String description;
    private String ubicacion;


    private List<Long> approvalRequired;
    private List<Long> assignedNodes;


    private List<Integer> createdAt;
    private String priority;
    private String recurrenceType;
    private Long company;
    private Long createdBy;
}
