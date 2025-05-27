package com.oauthapp.service;

import com.oauthapp.dao.UserRepository;
import com.oauthapp.dto.UserDto;
import com.oauthapp.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse registerUser(UserDto userDto) {
        return null;
    }
}
