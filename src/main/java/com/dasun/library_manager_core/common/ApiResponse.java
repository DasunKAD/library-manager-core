package com.dasun.library_manager_core.common;


public interface ApiResponse {
    String getMessage();

    void setMessage(String message);

    Object getPayload();

    void setPayload(Object payload);

    StatusCode getStatusCode();
}
