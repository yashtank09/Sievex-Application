package com.sievex.dto.response;

import lombok.Data;

@Data
public class SiteResponseDto {
    private Long siteId;
    private String siteName;
    private String siteDomain;
    private String siteUrl;
    private String siteDescription;
    private String siteCategory;
    private String siteCountry;
    private Boolean siteStatus;
    private String siteTypeName;
}
