package com.sievex.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User profile update DTO")
public class UserUpdateRequestDto {
    @JsonProperty("first-name")
    private String firstName;

    @JsonProperty("last-name")
    private String lastName;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("profile-completed")
    private boolean profileCompleted;
}
