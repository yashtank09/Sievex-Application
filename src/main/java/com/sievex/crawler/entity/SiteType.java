package com.sievex.crawler.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "m_site_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiteType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @Column(nullable = false, length = 50)
    private String name;  // e.g. ECOMMERCE

    @Column(nullable = false, unique = true, length = 50)
    private String alias; // Amazon, Flipkart

    @Column(columnDefinition = "TEXT")
    private String description; // Optional description of the site type

    private Boolean status = true; // Active or inactive status

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
