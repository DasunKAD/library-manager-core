package com.dasun.library_manager_core.api.exception;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String name, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", name, fieldName, fieldValue));
    }

}
