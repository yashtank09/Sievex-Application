package com.unifiedapp.crawler.repository;

import com.unifiedapp.crawler.entity.SiteData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<SiteData, Long> {
    // that's it ... no need to write any code LOL!
}
