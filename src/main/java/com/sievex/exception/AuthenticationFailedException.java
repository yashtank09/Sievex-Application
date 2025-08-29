package com.sievex.exception;

import com.sievex.constants.ApiResponseConstants;
import org.springframework.http.HttpStatus;

public class AuthenticationFailedException extends BaseException {
    public AuthenticationFailedException(String message) {
        super(
            message,
            "AUTHENTICATION_FAILED",
            ApiResponseConstants.STATUS_FAILURE,
            HttpStatus.UNAUTHORIZED.value()
        );
    }

    public AuthenticationFailedException(String message, Throwable cause) {
        super(
            message,
            cause,
            "AUTHENTICATION_FAILED",
            ApiResponseConstants.STATUS_FAILURE,
            HttpStatus.UNAUTHORIZED.value()
        );
    }
}
