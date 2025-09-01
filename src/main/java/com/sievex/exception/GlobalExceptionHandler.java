package com.sievex.exception;

import com.sievex.constants.ApiResponseConstants;
import com.sievex.dto.DataApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<DataApiResponse<Object>> handleBaseException(BaseException ex, WebRequest request) {
        logger.error("Handling {}: {}", ex.getClass().getSimpleName(), ex.getMessage());
        DataApiResponse<Object> response = new DataApiResponse<>(ex.getStatus(), ex.getHttpStatus(), ex.getMessage(), null, null);
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DataApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.warn("Validation error: {}", ex.getMessage());
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(fieldError -> fieldError.getField(), fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : ""));

        DataApiResponse<Map<String, String>> response = new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, HttpStatus.BAD_REQUEST.value(), "Validation failed", null, errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<DataApiResponse<Map<String, String>>> handleConstraintViolation(ConstraintViolationException ex) {
        logger.warn("Constraint violation: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            errors.put(field, violation.getMessage());
        });

        DataApiResponse<Map<String, String>> response = new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, HttpStatus.BAD_REQUEST.value(), "Constraint violation", null, errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<DataApiResponse<String>> handleAuthFailed(Exception ex) {
        DataApiResponse<String> response = new DataApiResponse<>(ApiResponseConstants.STATUS_ERROR, HttpStatus.UNAUTHORIZED.value(), "Unauthorized Exception: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<DataApiResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
        DataApiResponse<String> response = new DataApiResponse<>(ApiResponseConstants.STATUS_ERROR, 403, "Access Denied: " + ex.getMessage());
        return ResponseEntity.status(403).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<DataApiResponse<String>> handleRuntimeException(RuntimeException ex) {
        DataApiResponse<String> response = new DataApiResponse<>(ApiResponseConstants.STATUS_ERROR, 500, "Something went wrong: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DataApiResponse<String>> handleAllUncaughtException(Exception ex, WebRequest request) {
        logger.error("Unhandled exception occurred: {}", ex.getMessage(), ex);
        DataApiResponse<String> response = new DataApiResponse<>(ApiResponseConstants.STATUS_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred", null, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<DataApiResponse<Void>> handleExpiredJwtException(ExpiredJwtException ex) {
        logger.warn("Expired JWT token: {}", ex.getMessage());
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, HttpStatus.UNAUTHORIZED.value(), "Session has expired. Please log in again.", null), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<DataApiResponse<Void>> handleJwtException(JwtException ex) {
        logger.warn("JWT validation failed: {}", ex.getMessage());
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, HttpStatus.UNAUTHORIZED.value(), "Invalid authentication token: " + ex.getMessage(), null), HttpStatus.UNAUTHORIZED);
    }
}
