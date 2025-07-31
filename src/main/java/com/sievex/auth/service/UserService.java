package com.sievex.auth.service;

import com.sievex.dto.request.UserRequestDto;
import com.sievex.dto.response.UsersResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public UsersResponseDto registerUser(UserRequestDto userRequestDto);
    public UsersResponseDto getUserProfile(HttpServletRequest request);
    public UsersResponseDto findByUserName(String username);
    public UsersResponseDto getUserByUserName(String username);
    public UsersResponseDto getUserByUserNameOrEmail(String userName, String email);
    public boolean isUserExistByUserName(String userName);
    public boolean isUserExistByEmail(String email);
    public boolean isUserExistByPhone(String phone);
    public boolean isUserExistByUserNameOrEmail(String userName, String email);

}
