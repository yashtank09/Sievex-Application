package com.oauthapp.controller;

import com.oauthapp.dto.DataApiResponse;
import com.oauthapp.dto.UserResponse;
import com.oauthapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final HttpServletRequest request;

    @Autowired
    public UserController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }


    /**
     * Endpoint to fetch user profile information.
     *
     * @return ResponseEntity containing user profile data.
     */
    @GetMapping("/profile")
    public ResponseEntity<DataApiResponse<UserResponse>> getUserProfile() {
        UserResponse userResponse = userService.getUserProfile(request);
        return new ResponseEntity<>(new DataApiResponse<>("success", 200, "User profile fetched successfully.", userResponse), HttpStatus.OK);
    }
}
