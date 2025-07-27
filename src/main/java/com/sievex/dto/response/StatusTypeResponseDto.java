package com.sievex.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StatusTypeResponseDto {
    @NotNull(message = "Name must not be blank")
    private String name;
    @NotNull(message = "Alias must not be blank")
    private String alias;
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
    @NotNull(message = "Status must not be blank")
    private Boolean status;
}
