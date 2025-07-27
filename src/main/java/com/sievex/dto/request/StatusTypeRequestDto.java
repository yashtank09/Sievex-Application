package com.sievex.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StatusTypeRequestDto {
    @NotNull(message = "Name must not be blank")
    @Size(max = 120, message = "Name must not exceed 120 characters")
    @JsonProperty("name")
    private String name;
    @NotNull(message = "Status Type alias must not be blank")
    @JsonProperty("alias")
    private String alias;
    @Size(max = 500, message = "Status Type description must not exceed 500 characters")
    private String description;
    @NotNull(message = "Status Type status must not be blank")
    private Boolean status;
}
