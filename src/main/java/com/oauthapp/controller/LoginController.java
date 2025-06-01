package com.oauthapp.controller;

import com.oauthapp.dto.DataApiResponse;
import com.oauthapp.dto.LoginRequest;
import com.oauthapp.dto.UserResponse;
import com.oauthapp.entity.Users;
import com.oauthapp.repository.UserRepository;
import com.oauthapp.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    @Autowired
    public LoginController(UserRepository userRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/user")
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
