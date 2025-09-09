package com.sievex.auth.service.impl;

import com.sievex.auth.repository.UserRepository;
import com.sievex.auth.service.UserRoleService;
import com.sievex.auth.service.UserService;
import com.sievex.dto.request.UserRegistrationRequestDto;
import com.sievex.dto.response.UsersResponseDto;
import com.sievex.auth.entity.Users;
import com.sievex.auth.enums.UserRole;
import com.sievex.auth.enums.UserStatus;
import com.sievex.auth.enums.UserType;
import com.sievex.auth.utils.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, JWTUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    private Users prepareUserEntity(UserRegistrationRequestDto dto, UserRole role) {
        Users user = new Users();
        user.setUserName(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // Make sure to encode this!
        user.setProfileCompleted(false);
        user.setRole(userRoleService.getRoleByAlias(role.getRole()));
        user.setType(UserType.REGULAR.getValue());
        user.setStatus(UserStatus.INACTIVE.getValue());
        return user;
    }

    @Override
    public UsersResponseDto registerRegularUser(UserRegistrationRequestDto dto) {
        Users user = prepareUserEntity(dto, UserRole.USER);
        return setUserResponseData(userRepository.save(user));
    }

    @Override
    public UsersResponseDto registerModerator(UserRegistrationRequestDto dto) {
        Users user = prepareUserEntity(dto, UserRole.MODERATOR);
        return setUserResponseData(userRepository.save(user));
    }

    @Override
    public UsersResponseDto registerAdmin(UserRegistrationRequestDto dto) {
        Users user = prepareUserEntity(dto, UserRole.ADMIN);
        return setUserResponseData(userRepository.save(user));
    }

    @Override
    public UsersResponseDto getUserProfile(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = jwtUtil.extractTokenFromHeaderToken(authHeader);
        String userName = jwtUtil.extractUserName(token);
        return setUserResponseData(userRepository.findByUserName(userName));
    }

    @Override
    public UsersResponseDto findByUserName(String username) {
        return setUserResponseData(userRepository.findByUserName(username));
    }

    @Override
    public UsersResponseDto getUserByUserName(String username) {
        return setUserResponseData(userRepository.findByUserName(username));
    }

    @Override
    public UsersResponseDto getUserByUserNameOrEmail(String userName, String email) {
        return setUserResponseData(userRepository.findByUserNameOrEmail(userName, email));
    }

    @Override
    public boolean isUserExistByUserName(String userName) {
        return userName != null && userRepository.existsByUserName(userName.toLowerCase());
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        return email != null && userRepository.existsByEmail(email.toLowerCase());
    }

    @Override
    public boolean isUserExistByPhone(String phone) {
        return phone != null && userRepository.existsByPhone(phone.trim());
    }

    @Override
    public boolean isUserExistByUserNameOrEmail(String userName, String email) {
        return (userName != null && isUserExistByUserName(userName)) || 
               (email != null && isUserExistByEmail(email));
    }

    @Override
    public void updateUserPassword(String userName, String newPassword) {
        if (userRepository.updatePassword(userName, passwordEncoder.encode(newPassword)) == 0) {
            throw new IllegalArgumentException("Something went wrong!");
        }
    }

    private UsersResponseDto setUserResponseData(Users save) {
        UsersResponseDto response = new UsersResponseDto();
        response.setUserName(save.getUserName());
        response.setEmail(save.getEmail());
        response.setFirstName(save.getFirstName());
        response.setLastName(save.getLastName());
        response.setRole(save.getRole().getName());
        response.setPhone(save.getPhone());
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        String role = "ROLE_" + user.getRole().getName();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

        return new User(user.getUserName(), user.getPassword(), authorities);
    }
}
