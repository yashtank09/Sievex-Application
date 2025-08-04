package com.sievex.auth.service;

import com.sievex.dto.request.UserRegistrationRequestDto;
import com.sievex.dto.response.UsersResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UsersResponseDto registerRegularUser(UserRegistrationRequestDto dto);
    UsersResponseDto registerModerator(UserRegistrationRequestDto dto);
    UsersResponseDto registerAdmin(UserRegistrationRequestDto dto); // Optional, for internal use
    public UsersResponseDto getUserProfile(HttpServletRequest request);
    public UsersResponseDto findByUserName(String username);
    public UsersResponseDto getUserByUserName(String username);
    public UsersResponseDto getUserByUserNameOrEmail(String userName, String email);
    public boolean isUserExistByUserName(String userName);
    public boolean isUserExistByEmail(String email);
    public boolean isUserExistByPhone(String phone);
    public boolean isUserExistByUserNameOrEmail(String userName, String email);

}
