package com.sievex.crawler.repository;

import com.sievex.crawler.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Long> {
    // that's it ... no need to write any code LOL!
}
