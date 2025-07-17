package com.sievex.crawler.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "job_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Jobs job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_type_id")
    private StatusType statusType;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
    }
}
