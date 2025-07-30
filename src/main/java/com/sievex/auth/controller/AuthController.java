package com.sievex.auth.controller;

import com.sievex.auth.service.UserService;
import com.sievex.auth.utils.JWTUtil;
import com.sievex.dto.DataApiResponse;
import com.sievex.dto.request.LoginRequest;
import com.sievex.dto.request.UserRequestDto;
import com.sievex.dto.response.UsersResponseDto;
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

    public AuthController(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<DataApiResponse<UsersResponseDto>> registerUser(@RequestBody UserRequestDto userdata) {
        UsersResponseDto usersResponseDto = userService.registerUser(userdata);
        DataApiResponse<UsersResponseDto> response = new DataApiResponse<>("success", 200, "User registered successfully", usersResponseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("login/user")
    public ResponseEntity<DataApiResponse<UsersResponseDto>> login(@RequestBody LoginRequest loginRequest) {
        UsersResponseDto usersResponseDto = userService.findByUserName(loginRequest.getUsername());
        if (usersResponseDto == null || !usersResponseDto.getPassword().equals(loginRequest.getPassword())) {
            return new ResponseEntity<>(new DataApiResponse<>("fail", HttpStatus.UNAUTHORIZED.value(), "Invalid username or password"), HttpStatus.UNAUTHORIZED);
        }
        String token = jwtUtil.generateToken(usersResponseDto.getUserName(), usersResponseDto.getRole());
        return new ResponseEntity<>(new DataApiResponse<>("success", HttpStatus.OK.value(), "Login successful", token, usersResponseDto), HttpStatus.OK);
    }

}
