package com.sievex.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sievex.dto.DataApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Autowired
    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String errorMessage = "Unauthorized: " + authException.getMessage();
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;

        // Handle specific authentication exceptions
        if (authException.getCause() instanceof ExpiredJwtException) {
            errorMessage = "Session has expired. Please log in again.";
        } else if (authException instanceof BadCredentialsException) {
            errorMessage = "Invalid username or password";
        } else if (authException.getCause() != null && authException.getCause().getClass().getSimpleName().contains("JwtException")) {
            errorMessage = "Invalid authentication token";
        }

        DataApiResponse<String> errorResponse = new DataApiResponse<>("error", statusCode, errorMessage);

        response.setStatus(statusCode);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
