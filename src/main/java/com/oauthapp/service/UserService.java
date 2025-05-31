package com.oauthapp.service;

import com.oauthapp.dto.UserRequestDto;
import com.oauthapp.dto.UserResponse;

public interface UserService {
    public UserResponse registerUser(UserRequestDto userRequestDto);
}
