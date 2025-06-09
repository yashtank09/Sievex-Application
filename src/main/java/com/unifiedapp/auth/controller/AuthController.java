package com.unifiedapp.auth.controller;

import com.unifiedapp.dto.LoginRequest;
import com.unifiedapp.auth.entity.Users;
import com.unifiedapp.auth.repository.UserRepository;
import com.unifiedapp.dto.DataApiResponse;
import com.unifiedapp.dto.UserRequestDto;
import com.unifiedapp.dto.UserResponse;
import com.unifiedapp.auth.utils.JWTUtil;
import com.unifiedapp.auth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthController(UserService userService, JWTUtil jwtUtil, UserRepository userRepository) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<DataApiResponse<UserResponse>> registerUser(@RequestBody UserRequestDto userdata) {
        UserResponse userResponse = userService.registerUser(userdata);
        DataApiResponse<UserResponse> response = new DataApiResponse<>("success", 200, "User registered successfully", userResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("login/user")
    public ResponseEntity<DataApiResponse<UserResponse>> login(@RequestBody LoginRequest loginRequest) {
        Users user = userRepository.findByUserName(loginRequest.getUsername());
        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return new ResponseEntity<>(new DataApiResponse<>("fail", HttpStatus.UNAUTHORIZED.value(), "Invalid username or password"), HttpStatus.UNAUTHORIZED);
        }
        String token = jwtUtil.generateToken(user.getUserName(), user.getRole());
        UserResponse userResponse = new UserResponse(user.getUserName(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getPhone());

        return new ResponseEntity<>(new DataApiResponse<>("success", HttpStatus.OK.value(), "Login successful", token, userResponse), HttpStatus.OK);
    }

}
