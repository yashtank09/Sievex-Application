package com.sievex.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Login credentials for user authentication")
public class LoginRequest {
    @Schema(description = "Username of the user", example = "john.doe")
    @JsonProperty("user-name")
    private String username;
    @Schema(description = "Password of the user", example = "securePassword123!", format = "password")
    @JsonProperty("password")
    private String password;
}
