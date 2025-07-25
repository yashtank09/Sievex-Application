package com.sievex.crawler.repository;

import com.sievex.crawler.entity.SiteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "siteType", collectionResourceRel = "siteType")
public interface SiteTypeRepository extends JpaRepository<SiteType, Long> {
    SiteType findByAlias(String alias);
}
