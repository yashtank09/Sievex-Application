package com.sievex.crawler.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "m_sites")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String domain;

    @Column(length = 255)
    private String url;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String category;

    @Column(length = 50)
    private String country;

    @Column(nullable = false)
    private Boolean status = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_type_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private SiteType siteType;

    @Column(name = "crawler_class_name", nullable = false, length = 255)
    private String crawlerClassName;

    @Column(name = "extractor_class_name", nullable = false, length = 255)
    private String extractorClassName;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Site(String name, SiteType siteType, String domain, String url, String description, String category, String country, Boolean status) {
        this.name = name;
        this.siteType = siteType;
        this.domain = domain;
        this.url = url;
        this.description = description;
        this.category = category;
        this.country = country;
        this.status = status;
    }
}
