package com.sievex.exception;

import com.sievex.constants.ApiResponseConstants;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class ValidationException extends BaseException {
    private final Map<String, String> validationErrors;

    public ValidationException(String message, Map<String, String> validationErrors) {
        super(
            message,
            "VALIDATION_FAILED",
            ApiResponseConstants.STATUS_FAILURE,
            HttpStatus.BAD_REQUEST.value()
        );
        this.validationErrors = validationErrors;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }
}
