package com.sievex.crawler.repository;

import com.sievex.crawler.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Long> {
    // Find site by exact domain (e.g., "amazon.in")
    Site findByDomain(String domain);
}
