package com.sievex.auth.controller;

import com.sievex.auth.service.UserService;
import com.sievex.auth.utils.JWTUtil;
import com.sievex.constants.ApiResponseConstants;
import com.sievex.dto.DataApiResponse;
import com.sievex.dto.request.LoginRequest;
import com.sievex.dto.request.UserRequestDto;
import com.sievex.dto.response.UsersResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("user/register")
    @Operation(
            summary = "Register User",
            description = "Registers a new user with the specified details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = DataApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - User with provided details already exists",
                    content = @Content(schema = @Schema(implementation = DataApiResponse.class)))
    })
    public ResponseEntity<DataApiResponse<UsersResponseDto>> registerUser(@RequestBody UserRequestDto userdata) {
        DataApiResponse<UsersResponseDto> response = validateUserRegisterRequest(userdata);
        if (response == null) {
            UsersResponseDto usersResponseDto = userService.registerUser(userdata);
            response = new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, "User registered successfully.", usersResponseDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private DataApiResponse<UsersResponseDto> validateUserRegisterRequest(UserRequestDto usersResponseDto) {
        if (userService.isUserExistByUserName(usersResponseDto.getUsername())) {
            return new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, "User already exist with username " + usersResponseDto.getUsername());
        } else if (userService.isUserExistByEmail(usersResponseDto.getEmail())) {
            return new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, "User already exist with email " + usersResponseDto.getEmail());
        } else if (userService.isUserExistByPhone(usersResponseDto.getPhone())) {
            return new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, "User already exist with phone " + usersResponseDto.getPhone());
        }
        return null;
    }

    @PostMapping("user/login")
    @Operation(
            summary = "Authenticate user",
            description = "Authenticates a user and returns a JWT token for authorization"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = DataApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials",
                    content = @Content(schema = @Schema(implementation = DataApiResponse.class)))
    })
    public ResponseEntity<DataApiResponse<UsersResponseDto>> login(@RequestBody LoginRequest loginRequest) {
        UsersResponseDto usersResponseDto = userService.findByUserName(loginRequest.getUsername());
        if (usersResponseDto == null || !usersResponseDto.getPassword().equals(loginRequest.getPassword())) {
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, HttpStatus.UNAUTHORIZED.value(), "Invalid username or password"), HttpStatus.UNAUTHORIZED);
        }
        String token = jwtUtil.generateToken(usersResponseDto.getUserName(), usersResponseDto.getRole());
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, "Login successful", token, usersResponseDto), HttpStatus.OK);
    }

}
