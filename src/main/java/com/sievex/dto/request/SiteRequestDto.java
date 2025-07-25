package com.sievex.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SiteRequestDto {
    @NotNull(message = "Site name must not be blank")
    @Size(max = 100, message = "Site name must not exceed 100 characters")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Site domain must not be blank")
    @Size(max = 100, message = "Site domain must not exceed 100 characters")
    @JsonProperty("domain")
    private String domain;

    @NotNull(message = "Site URL must not be blank")
    @Size(max = 2048, message = "Site URL must not exceed 2048 characters")
    @JsonProperty("url")
    private String url;

    @Size(max = 500, message = "Site description must not exceed 500 characters")
    @JsonProperty("description")
    private String description;

    @NotNull(message = "Site category must not be blank")
    @JsonProperty("category")
    private String category;

    @NotNull(message = "Site country must not be blank")
    @JsonProperty("country")
    private String country;

    @NotNull(message = "Site status must not be blank")
    @JsonProperty("status")
    private Boolean status;

    @NotNull(message = "Site type must not be blank")
    @JsonProperty("site-type")
    private String siteType;

    @JsonProperty("crawler-class-name")
    private String crawlerClassName;
    
    @JsonProperty("extractor-class-name")
    private String extractorClassName;
}
