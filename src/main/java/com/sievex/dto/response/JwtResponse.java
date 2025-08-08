package com.sievex.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "JWT authentication response")
public class JwtResponse {
    @Schema(description = "JWT token for authentication")
    private String token;
    @Schema(description = "Authenticated user data")
    private UsersResponseDto user;
}