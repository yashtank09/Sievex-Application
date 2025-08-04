package com.sievex.auth.controller;

import com.sievex.auth.service.UserService;
import com.sievex.dto.DataApiResponse;
import com.sievex.dto.request.UserRegistrationRequestDto;
import com.sievex.dto.response.UsersResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')") // restrict access to ADMIN only
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-moderator")
    public ResponseEntity<DataApiResponse<UsersResponseDto>> createModerator(@RequestBody UserRegistrationRequestDto request) {
        if (userService.isUserExistByUserNameOrEmail(request.getUsername(), request.getEmail())) {
            return ResponseEntity.badRequest().body(
                    new DataApiResponse<>("FAILURE", 400, "Username or email already exists")
            );
        }

        UsersResponseDto created = userService.registerModerator(request);
        return ResponseEntity.ok(
                new DataApiResponse<>("SUCCESS", 200, "Moderator created", created)
        );
    }
}
