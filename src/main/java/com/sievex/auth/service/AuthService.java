package com.sievex.auth.service;

import com.sievex.dto.request.LoginRequest;
import com.sievex.dto.request.PasswordResetRequestDto;
import com.sievex.dto.response.JwtResponse;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
    String generateJwtToken(String username);
    void invalidateToken(String token);

    void resetPassword(PasswordResetRequestDto resetPasswordRequest);
}
