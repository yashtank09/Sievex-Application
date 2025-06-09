package com.unifiedapp.auth.service;

import com.unifiedapp.dto.UserRequestDto;
import com.unifiedapp.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public UserResponse registerUser(UserRequestDto userRequestDto);
    public UserResponse getUserProfile(HttpServletRequest request);
}
