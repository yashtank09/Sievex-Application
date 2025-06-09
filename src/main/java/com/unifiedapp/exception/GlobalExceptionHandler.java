package com.unifiedapp.exception;

import com.unifiedapp.dto.DataApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
    public ResponseEntity<DataApiResponse> handleRuntimeException(RuntimeException ex) {
        DataApiResponse<String> response = new DataApiResponse<>("error", 500, "Something went wrong: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<DataApiResponse> handleAccessDeniedException(AccessDeniedException ex) {
        DataApiResponse<String> response = new DataApiResponse<>("error",403,"Access Denied: " + ex.getMessage());
        return ResponseEntity.status(403).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DataApiResponse> handleGenericException(Exception ex) {
        DataApiResponse<String> response = new DataApiResponse<>("error", 500, "Unhandled Exception: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
