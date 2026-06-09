package com.jeseg.admin_system.task.domain.dto;

import lombok.Getter;

@Getter
public enum ApprovalStatus {
    PENDING("PND"),
    APPROVED("APR"),
    REJECTED("REJ");

    private final String code;

    ApprovalStatus(String code) {
        this.code = code;
    }

    public static ApprovalStatus fromCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException("code must not be null");
        }
        return switch (code.trim().toUpperCase()) {
            case "PND" -> PENDING;
            case "APR" -> APPROVED;
            case "REJ" -> REJECTED;
            default -> throw new IllegalArgumentException("Unknown priority code: " + code);
        };
    }
}
