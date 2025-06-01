package com.oauthapp.service;

import com.oauthapp.dto.UserRequestDto;
import com.oauthapp.dto.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public UserResponse registerUser(UserRequestDto userRequestDto);
}
