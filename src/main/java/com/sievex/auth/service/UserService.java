package com.sievex.auth.service;

import com.sievex.dto.UserRequestDto;
import com.sievex.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public UserResponse registerUser(UserRequestDto userRequestDto);
    public UserResponse getUserProfile(HttpServletRequest request);
}
