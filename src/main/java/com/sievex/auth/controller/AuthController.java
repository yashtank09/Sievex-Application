package com.sievex.auth.controller;

import com.sievex.auth.service.AuthService;
import com.sievex.constants.ApiResponseConstants;
import com.sievex.dto.DataApiResponse;
import com.sievex.dto.request.LoginRequest;
import com.sievex.dto.response.JwtResponse;
import com.sievex.dto.response.UsersResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("user/login")
    @Operation(
            summary = "Authenticate user",
            description = "Authenticates a user and returns a JWT token for authorization"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = DataApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials",
                    content = @Content(schema = @Schema(implementation = DataApiResponse.class)))
    })
    public ResponseEntity<DataApiResponse<UsersResponseDto>> login(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, "Login successful", jwtResponse.getToken(), jwtResponse.getUser()), HttpStatus.OK);
    }

    @PostMapping("user/logout")
    public ResponseEntity<DataApiResponse<Void>> logout(HttpServletRequest servletRequest) {
        try {
            String authHeader = servletRequest.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                authService.invalidateToken(token);
                return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, "Logout successful", null), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, "Logout failed", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, "Logout failed", null), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("user/forgot-password")
    public ResponseEntity<DataApiResponse<Void>> forgotPassword() {
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, "Password reset link sent successfully", null), HttpStatus.OK);
    }

    @PostMapping("user/reset-password")
    public ResponseEntity<DataApiResponse<Void>> resetPassword() {
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, "Password reset successfully", null), HttpStatus.OK);
    }

    @PostMapping("user/change-password")
    public ResponseEntity<DataApiResponse<Void>> changePassword() {
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, "Password changed successfully", null), HttpStatus.OK);
    }
}
