package com.jeseg.admin_system.task.domain.dto;

import lombok.Getter;

@Getter
public enum RecurrenceType {
    DAILY("D"),
    WEEKLY("W"),
    MONTHLY("M");

    private final String code;

    RecurrenceType(String code) {
        this.code = code;
    }

    public static RecurrenceType fromCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException("code must not be null");
        }
        return switch (code.trim().toUpperCase()) {
            case "D" -> DAILY;
            case "W" -> WEEKLY;
            case "M" -> MONTHLY;
            default -> throw new IllegalArgumentException("Unknown priority code: " + code);
        };
    }
}
