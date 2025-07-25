package com.sievex.dto.request;

import lombok.Data;

@Data
public class JobsRequestDto {
    private String name;
    private String url;
    private String description;
    private String jobType;
    private String siteDomain;
    private String priority;
}
