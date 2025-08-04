package com.sievex.exception;

import com.sievex.constants.ApiResponseConstants;
import com.sievex.dto.DataApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    // This class can be used to handle global exceptions across the application
    // You can define methods annotated with @ExceptionHandler to handle specific exceptions
    // and return appropriate responses.

    // Example:
    // @ExceptionHandler(ResourceNotFoundException.class)
    // public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
    //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    // }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<DataApiResponse<String>> handleRuntimeException(RuntimeException ex) {
        DataApiResponse<String> response = new DataApiResponse<>(ApiResponseConstants.STATUS_ERROR, 500, "Something went wrong: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<DataApiResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
        DataApiResponse<String> response = new DataApiResponse<>(ApiResponseConstants.STATUS_ERROR,403,"Access Denied: " + ex.getMessage());
        return ResponseEntity.status(403).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DataApiResponse<String>> handleGenericException(Exception ex) {
        DataApiResponse<String> response = new DataApiResponse<>(ApiResponseConstants.STATUS_ERROR, 500, "Unhandled Exception: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<DataApiResponse<String>> handleAuthFailed(Exception ex) {
        DataApiResponse<String> response = new DataApiResponse<>(ApiResponseConstants.STATUS_ERROR, HttpStatus.UNAUTHORIZED.value(), "Unauthorized Exception: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
