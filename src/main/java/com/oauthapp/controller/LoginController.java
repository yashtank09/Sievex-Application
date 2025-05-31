package com.oauthapp.controller;

import com.oauthapp.entity.Users;
import com.oauthapp.repository.UserRepository;
import com.oauthapp.util.UserRoles;
import com.oauthapp.util.UserType;
import com.oauthapp.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
