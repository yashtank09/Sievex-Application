package com.oauthapp.controller;

import com.oauthapp.entity.Users;
import com.oauthapp.repository.UserRepository;
import com.oauthapp.util.UserRoles;
import com.oauthapp.util.UserType;
import com.oauthapp.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/post-login")
    public String postLogin(OAuth2AuthenticationToken token) {
        String email = token.getPrincipal().getAttribute("email");
        Users user = userRepository.findByEmail(email).orElseGet(() -> {
            Users newUser = new Users();
            newUser.setEmail(email);
            newUser.setUserName(UserUtils.generateUniqueUsername(token.getPrincipal().getAttribute("email")));
            newUser.setType(UserType.USER.getType());
            newUser.setRole(UserRoles.USER.getRole());
            newUser.setProfileCompleted(false);
            newUser.setStatus("active");
            return userRepository.save(newUser);
        });
        if (!user.isProfileCompleted()) {
            return "Please complete your profile."; // Redirect to profile completion page
        }
        return "Welcome, " + user.getFirstName() + "!";
    }

    @GetMapping("/user")
    public Users showProfileForm(OAuth2AuthenticationToken token) {
        String email = token.getPrincipal().getAttribute("email");
        return userRepository.findByEmail(email).orElseThrow();
    }

    @PatchMapping("/complete-profile")
    public String saveProfile(@RequestBody Users userForm) {
        Users user = userRepository.findByEmail(userForm.getEmail()).orElseThrow();
        user.setUserName(userForm.getUserName());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setPhone(userForm.getPhone());
        user.setPassword(userForm.getPassword());
        user.setProfileCompleted(true);
        userRepository.save(user);
        return "Profile updated successfully!";
    }
}
