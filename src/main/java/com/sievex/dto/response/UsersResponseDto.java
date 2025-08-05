package com.sievex.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersResponseDto {
    @JsonProperty("user-name")
    private String userName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("first-name")
    private String firstName;
    @JsonProperty("last-name")
    private String lastName;
    @JsonProperty("role")
    private String role;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("is-profile-completed")
    private boolean profileCompleted;
}