package com.oauthapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @JsonProperty("user-name")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("first-name")
    private String firstName;
    @JsonProperty("last-name")
    private String lastName;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("password")
    private String password;
    @JsonProperty("role")
    private String role;
    @JsonProperty("type")
    private String type;
    @JsonProperty("status")
    private String status;
}