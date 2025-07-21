package com.sievex.crawler.repository;

import com.sievex.crawler.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiteRepository extends JpaRepository<Site, Long> {
    // Find site by exact domain (e.g., "amazon.in")
    Site findByDomain(String domain);

    // Find all active sites
    List<Site> findAllByStatus_Alias(String statusAlias);

    // Find by type (e.g., "ECOMMERCE")
    List<Site> findAllBySiteType_Alias(String typeAlias);
}
