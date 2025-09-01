package com.sievex.auth.service.impl;

import com.sievex.auth.service.AuthService;
import com.sievex.auth.service.TokenService;
import com.sievex.auth.service.UserService;
import com.sievex.auth.utils.JWTUtil;
import com.sievex.dto.request.LoginRequest;
import com.sievex.dto.request.PasswordResetRequestDto;
import com.sievex.dto.response.JwtResponse;
import com.sievex.dto.response.UsersResponseDto;
import com.sievex.exception.AuthenticationFailedException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Data
@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final TokenService tokenService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserService userService,
                           JWTUtil jwtUtil, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
    }

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new AuthenticationFailedException("Invalid Username or password");
        } catch (AuthenticationServiceException e) {
            throw new AuthenticationServiceException("Authentication failed!");
        }

        // Fetch user to get role (since Authentication might not contain custom fields)
        UsersResponseDto user = userService.getUserByUserName(loginRequest.getUsername());

        String token = generateJwtToken(user.getUserName());

        return new JwtResponse(token, user);
    }

    private boolean validateUserCredentials(String username, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            return true;
        } catch (AuthenticationException ex) {
            return false;
        }
    }

    @Override
    public String generateJwtToken(String username) {
        UsersResponseDto user = userService.getUserByUserName(username);
        return jwtUtil.generateToken(user.getUserName(), user.getRole(), user.getEmail());
    }

    @Override
    public void invalidateToken(String token) {
        // Add token to blacklist or mark as invalid in Redis
        // Remove 'Bearer ' prefix if present
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Get token expiration time and blacklist the token
        Date expirationTime = jwtUtil.extractExpirationTime(token);
        if (jwtUtil.isTokenExpired(token)) {
            tokenService.blacklistToken(token, expirationTime.getTime());
        }
    }

    @Override
    public void resetPassword(PasswordResetRequestDto resetPasswordRequest) {
        if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        if (!validateUserCredentials(userName, resetPasswordRequest.getOldPassword())) {
            throw new AuthenticationFailedException("Current password is incorrect");
        }

        userService.updateUserPassword(userName, resetPasswordRequest.getNewPassword());
    }
}
