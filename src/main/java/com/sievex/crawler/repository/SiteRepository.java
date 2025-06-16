package com.sievex.crawler.repository;

import com.sievex.crawler.entity.SiteData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<SiteData, Long> {
    // that's it ... no need to write any code LOL!
}
