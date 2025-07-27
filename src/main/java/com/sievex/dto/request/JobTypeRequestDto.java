package com.sievex.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JobTypeRequestDto {
    @NotNull(message = "Name must not be blank")
    @Size(max = 120, message = "Name must not exceed 120 characters")
    @JsonProperty("name")
    private String name;
    @NotNull(message = "Job Type alias must not be blank")
    @JsonProperty("alias")
    private String alias;
    @Size(max = 500, message = "Job Type description must not exceed 500 characters")
    private String description;
    @NotNull(message = "Job Type status must not be blank")
    private Boolean status;
}
