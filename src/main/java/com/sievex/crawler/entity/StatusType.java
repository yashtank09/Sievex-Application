package com.sievex.crawler.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "status_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @Column(nullable = false, unique = true, length = 50)
    private String code; // PENDING, RUNNING, etc.

    @Column(nullable = false, length = 100)
    private String label; // Human-readable form: Pending, Running

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
