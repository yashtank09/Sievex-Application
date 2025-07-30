package com.sievex.auth.service;

import com.sievex.auth.repository.UserRepository;
import com.sievex.dto.request.UserRequestDto;
import com.sievex.dto.response.UsersResponseDto;
import com.sievex.auth.entity.Users;
import com.sievex.auth.enums.UserRole;
import com.sievex.auth.enums.UserStatus;
import com.sievex.auth.enums.UserType;
import com.sievex.auth.utils.JWTUtil;
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
        userData.setRole(UserRole.USER.getRole());
        userData.setType(UserType.REGULAR.getValue());
        userData.setStatus(UserStatus.INACTIVE.getValue());
        return userData;
    }

    @Override
    public UsersResponseDto registerUser(UserRequestDto userRequestDto) {
        return setUserResponseData(userRepository.save(setRegisterUserData(userRequestDto)));
    }

    @Override
    public UsersResponseDto getUserProfile(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = jwtUtil.extractTokenFromHeader(authHeader);
        String userName = jwtUtil.extractUserName(token);
        return setUserResponseData(userRepository.findByUserName(userName));
    }

    @Override
    public UsersResponseDto findByUserName(String username) {
        return setUserResponseData(userRepository.findByUserName(username));
    }

    private UsersResponseDto setUserResponseData(Users save) {
        UsersResponseDto response = new UsersResponseDto();
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
