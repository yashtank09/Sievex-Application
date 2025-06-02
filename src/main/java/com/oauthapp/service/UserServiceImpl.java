package com.oauthapp.service;

import com.oauthapp.repository.UserRepository;
import com.oauthapp.dto.UserRequestDto;
import com.oauthapp.dto.UserResponse;
import com.oauthapp.entity.Users;
import com.oauthapp.enums.UserRole;
import com.oauthapp.enums.UserStatus;
import com.oauthapp.enums.UserType;
import com.oauthapp.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    private Users setRegisterUserData(UserRequestDto userRequestData) {
        Users userData = new Users();
        userData.setUserName(userRequestData.getUsername());
        userData.setEmail(userRequestData.getEmail());
        userData.setFirstName(userRequestData.getFirstName());
        userData.setLastName(userRequestData.getLastName());
        userData.setPhone(userRequestData.getPhone());
        userData.setPassword(userRequestData.getPassword());
        userData.setProfileCompleted(false);
        userData.setRole(UserRole.USER.getValue());
        userData.setType(UserType.REGULAR.getValue());
        userData.setStatus(UserStatus.INACTIVE.getValue());
        return userData;
    }

    @Override
    public UserResponse registerUser(UserRequestDto userRequestDto) {
        return setUserResponseData(userRepository.save(setRegisterUserData(userRequestDto)));
    }

    @Override
    public UserResponse getUserProfile(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = jwtUtil.extractTokenFromHeader(authHeader);
        String userName = jwtUtil.extractUserName(token);
        return setUserResponseData(userRepository.findByUserName(userName));
    }

    private UserResponse setUserResponseData(Users save) {
        UserResponse response = new UserResponse();
        response.setUserName(save.getUserName());
        response.setEmail(save.getEmail());
        response.setFirstName(save.getFirstName());
        response.setLastName(save.getLastName());
        response.setPhone(save.getPhone());
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUserName(username);
        return new User(user.getUserName(), user.getPassword(), Collections.emptyList());
    }
}
