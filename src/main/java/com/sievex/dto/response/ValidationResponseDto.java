package com.sievex.dto.response;

import lombok.Data;

@Data
public class ValidationResponseDto {
    private boolean userNameExists;
    private boolean emailExists;
    private boolean phoneExists;
}
