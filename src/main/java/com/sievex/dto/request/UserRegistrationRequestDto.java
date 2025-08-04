package com.sievex.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User registration request data transfer object")
public class UserRegistrationRequestDto {
    @Schema(description = "Username for the new account", example = "john.doe")
    @JsonProperty("user-name")
    private String username;

    @Schema(description = "Email address of the user", example = "john.doe@example.com", format = "email")
    @JsonProperty("email")
    private String email;

    @Schema(description = "User's first name", example = "John")
    @JsonProperty("first-name")
    private String firstName;

    @Schema(description = "User's last name", example = "Doe")
    @JsonProperty("last-name")
    private String lastName;

    @Schema(description = "User's phone number", example = "+1234567890")
    @JsonProperty("phone")
    private String phone;
    
    @Schema(description = "Password for the new account (must be at least 8 characters)", 
            example = "securePassword123!",
            minLength = 8,
            format = "password")
    @JsonProperty("password")
    private String password;

    @JsonIgnore
    public String getPassword() {
        return password;
    }
}
