package com.unifiedapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRequestDto {
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
    @JsonProperty("profile-completed")
    private boolean profileCompleted;
    @JsonProperty("password")
    private String password;
    @JsonIgnore
    @JsonProperty("role")
    private String role;
    @JsonIgnore
    @JsonProperty("type")
    private String type;
    @JsonIgnore
    @JsonProperty("status")
    private String status;
    @JsonIgnore
    @JsonProperty("created-at")
    private String createdAt;
    @JsonIgnore
    @JsonProperty("updated-at")
    private String updatedAt;
}
