package com.sievex.auth.service;

import com.sievex.dto.request.LoginRequest;
import com.sievex.dto.response.JwtResponse;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
    void validateUserCredentials(String username, String password);
    String generateJwtToken(String username);
    void invalidateToken(String token);
}
