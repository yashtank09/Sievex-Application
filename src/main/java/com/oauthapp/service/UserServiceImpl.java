package com.oauthapp.service;

import com.oauthapp.repository.UserRepository;
import com.oauthapp.dto.UserRequestDto;
import com.oauthapp.dto.UserResponse;
import com.oauthapp.entity.Users;
import com.oauthapp.enums.UserRole;
import com.oauthapp.enums.UserStatus;
import com.oauthapp.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        return setRegistrationResponseData(userRepository.save(setRegisterUserData(userRequestDto)));
    }

    private UserResponse setRegistrationResponseData(Users save) {
        UserResponse response = new UserResponse();
        response.setUserName(save.getUserName());
        response.setEmail(save.getEmail());
        response.setFirstName(save.getFirstName());
        response.setLastName(save.getLastName());
        response.setPhone(save.getPhone());
        response.setStatus(save.getStatus());
        return response;
    }
}
