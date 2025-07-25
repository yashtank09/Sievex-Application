package com.sievex.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JobsRequestDto {
    @NotNull(message = "Job name must not be blank")
    @Size(max = 120, message = "Job name must not exceed 120 characters")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Job URL must not be blank")
    @Size(max = 2048, message = "Job URL must not exceed 2048 characters")
    @JsonProperty("url")
    private String url;

    @Size(max = 500, message = "Job description must not exceed 500 characters")
    @JsonProperty("description")
    private String description;

    @NotNull(message = "Job type must not be blank")
    @JsonProperty("job-type")
    private String jobType;

    @NotNull(message = "Priority must not be blank")
    @JsonProperty("priority")
    private int priority;
}
