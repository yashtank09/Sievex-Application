package com.sievex.exception;

import com.sievex.constants.ApiResponseConstants;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException {
    public UserAlreadyExistsException(String fieldName, String value) {
        super(
            String.format("User with %s '%s' already exists", fieldName, value),
            "USER_ALREADY_EXISTS",
            ApiResponseConstants.STATUS_FAILURE,
            HttpStatus.CONFLICT.value()
        );
    }

    public UserAlreadyExistsException(String message) {
        super(
            message,
            "USER_ALREADY_EXISTS",
            ApiResponseConstants.STATUS_FAILURE,
            HttpStatus.CONFLICT.value()
        );
    }
}
