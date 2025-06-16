package com.sievex.dto;

import lombok.Data;

@Data
public class SiteDataResponseDto {
    private Long siteId;
    private String siteName;
    private String siteTypeName;
    private String siteDomain;
    private String siteUrl;
    private String siteDescription;
    private String siteCategory;
    private String siteCountry;
    private String siteStatus;
}
