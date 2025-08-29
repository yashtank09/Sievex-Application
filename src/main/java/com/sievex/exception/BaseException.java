package com.sievex.exception;

import com.sievex.constants.ApiResponseConstants;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final String errorCode;
    private final String status;
    private final int httpStatus;

    public BaseException(String message, String errorCode, String status, int httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
        this.httpStatus = httpStatus;
    }

    public BaseException(String message, Throwable cause, String errorCode, String status, int httpStatus) {
        super(message, cause);
        this.errorCode = errorCode;
        this.status = status;
        this.httpStatus = httpStatus;
    }
}
