package com.dasun.library_manager_core.api.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private String details;
    private String status;

    public ErrorResponse(String message, String details, String status) {
        this.message = message;
        this.details = details;
        this.status = status;
    }
}