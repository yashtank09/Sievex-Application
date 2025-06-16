package com.sievex.crawler.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="m_site_data")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SiteData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "site_id")
    private Long siteId;
    @Column(name = "site_name")
    private String siteName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_type_id", referencedColumnName = "type_id", nullable = false)
    private SiteType siteType;
    @Column(name = "site_domain")
    private String siteDomain;
    @Column(name = "site_url")
    private String siteUrl;
    @Column(name = "site_description")
    private String siteDescription;
    @Column(name = "site_category")
    private String siteCategory;
    @Column(name = "site_country")
    private String siteCountry;
    @Column(name = "site_status")
    private String siteStatus;

    public SiteData(String siteName, SiteType siteType, String siteDomain, String siteUrl, String siteDescription, String siteCategory, String siteCountry, String siteStatus) {
        this.siteName = siteName;
        this.siteType = siteType;
        this.siteDomain = siteDomain;
        this.siteUrl = siteUrl;
        this.siteDescription = siteDescription;
        this.siteCategory = siteCategory;
        this.siteCountry = siteCountry;
        this.siteStatus = siteStatus;
    }
}
