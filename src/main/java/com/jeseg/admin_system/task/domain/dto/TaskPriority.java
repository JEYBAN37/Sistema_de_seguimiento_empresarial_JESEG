package com.jeseg.admin_system.task.domain.dto;

public enum TaskPriority {
    LOW("L"),
    MEDIUM("M"),
    HIGH("H");

    private final String code;

    TaskPriority(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static TaskPriority fromCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException("code must not be null");
        }
        return switch (code.trim().toUpperCase()) {
            case "L" -> LOW;
            case "M" -> MEDIUM;
            case "H" -> HIGH;
            default -> throw new IllegalArgumentException("Unknown priority code: " + code);
        };
    }
}