package com.sievex.auth.controller;

import com.sievex.auth.service.AuthService;
import com.sievex.constants.ApiResponseConstants;
import com.sievex.dto.DataApiResponse;
import com.sievex.dto.request.PasswordResetRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class PasswordController {

    private final AuthService authService;

    public PasswordController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("user/forgot-password")
    public ResponseEntity<DataApiResponse<Void>> forgotPassword() {
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, "Password reset link sent successfully", null), HttpStatus.OK);
    }

    @PostMapping("user/reset-password")
    public ResponseEntity<DataApiResponse<Void>> resetPassword(@RequestBody PasswordResetRequestDto resetPasswordRequest) {
        authService.resetPassword(resetPasswordRequest);
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, "Password reset successfully", null), HttpStatus.OK);
    }

    @PostMapping("user/change-password")
    public ResponseEntity<DataApiResponse<Void>> changePassword() {
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, "Password changed successfully", null), HttpStatus.OK);
    }

}
