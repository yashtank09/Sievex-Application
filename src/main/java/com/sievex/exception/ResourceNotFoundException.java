package com.sievex.exception;

import com.sievex.constants.ApiResponseConstants;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(
            String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue),
            "RESOURCE_NOT_FOUND",
            ApiResponseConstants.STATUS_FAILURE,
            HttpStatus.NOT_FOUND.value()
        );
    }

    public ResourceNotFoundException(String message) {
        super(
            message,
            "RESOURCE_NOT_FOUND",
            ApiResponseConstants.STATUS_FAILURE,
            HttpStatus.NOT_FOUND.value()
        );
    }
}
