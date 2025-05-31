package com.oauthapp.controller;

import com.oauthapp.repository.UserRepository;
import com.oauthapp.dto.DataApiResponse;
import com.oauthapp.dto.UserRequestDto;
import com.oauthapp.dto.UserResponse;
import com.oauthapp.util.JWTUtil;
import com.oauthapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<DataApiResponse<UserResponse>> registerUser(@RequestBody UserRequestDto userdata) {
        UserResponse userResponse = userService.registerUser(userdata);
        DataApiResponse<UserResponse> response = new DataApiResponse<>("success", 200, "User registered successfully", userResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
