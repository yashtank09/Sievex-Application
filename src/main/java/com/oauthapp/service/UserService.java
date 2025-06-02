package com.oauthapp.service;

import com.oauthapp.dto.UserRequestDto;
import com.oauthapp.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public UserResponse registerUser(UserRequestDto userRequestDto);
    public UserResponse getUserProfile(HttpServletRequest request);
}
