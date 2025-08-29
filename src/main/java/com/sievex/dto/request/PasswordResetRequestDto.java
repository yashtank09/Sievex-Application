package com.sievex.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PasswordResetRequestDto {
    @JsonProperty("old-password")
    private String oldPassword;
    @JsonProperty("new-password")
    private String newPassword;
    @JsonProperty("confirm-password")
    private String confirmPassword;
}
