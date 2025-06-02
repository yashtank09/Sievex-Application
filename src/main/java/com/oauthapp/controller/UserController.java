package com.oauthapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {


    @GetMapping("/profile")
    public String getUserProfile() {
        // This method would typically return user profile information
        return "User Profile Information";
    }
}
