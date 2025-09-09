package com.sievex.auth.service;

import com.sievex.dto.request.LoginRequest;
import com.sievex.dto.request.PasswordResetRequestDto;
import com.sievex.dto.response.JwtResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
    String generateJwtToken(String username);
    void invalidateToken(String token);
    String extractTokenFromRequest(String request);
    void resetPassword(PasswordResetRequestDto resetPasswordRequest);

    boolean validateToken(String token);
}
