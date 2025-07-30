package com.sievex.auth.service;

import com.sievex.dto.request.UserRequestDto;
import com.sievex.dto.response.UsersResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public UsersResponseDto registerUser(UserRequestDto userRequestDto);
    public UsersResponseDto getUserProfile(HttpServletRequest request);
    public UsersResponseDto findByUserName(String username);
}
