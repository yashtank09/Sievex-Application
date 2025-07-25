package com.sievex.dto.response;

import lombok.Data;

@Data
public class JobsResponseDto {
    private String name;
    private String jobType;
    private String status;
    private String description;
    private String site;
    private String jobCreatedAt;
    private String jobUpdatedAt;
}
