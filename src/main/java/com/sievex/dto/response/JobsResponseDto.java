package com.sievex.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JobsResponseDto {
    @NotNull(message = "Job name must not be blank")
    @Size(max = 120, message = "Job name must not exceed 120 characters")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Job URL must not be blank")
    @Size(max = 2048, message = "URL must not exceed 2048 characters")
    private String domain;

    @NotNull(message = "Job URL must not be blank")
    private String url;

    @NotNull(message = "Job type must not be blank")
    private String jobType;

    @NotNull(message = "Priority must not be blank")
    private String status;

    @Size(max = 500, message = "Job description must not exceed 500 characters")
    private String description;
}
