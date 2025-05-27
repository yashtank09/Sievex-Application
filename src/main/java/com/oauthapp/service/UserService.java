package com.oauthapp.service;

import com.oauthapp.dto.UserDto;
import com.oauthapp.dto.UserResponse;

import java.util.List;

public interface UserService {
    public UserResponse registerUser(UserDto userDto);
}
