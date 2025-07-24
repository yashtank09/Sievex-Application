package com.sievex.dto;

import lombok.Data;

@Data
public class JobsDataResponseDto {
    private Long jobId;
    private String jobName;
    private String jobType;
    private String jobStatus;
    private String jobDescription;
    private String jobSite;
    private String jobCreatedAt;
    private String jobUpdatedAt;
}
