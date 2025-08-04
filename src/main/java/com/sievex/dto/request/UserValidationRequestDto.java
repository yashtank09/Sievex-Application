package com.sievex.dto.request;

import lombok.Data;

@Data
public class UserValidationRequestDto {
    private String userName;
    private String email;
    private String phone;
}
