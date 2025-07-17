package com.sievex.crawler.service;
import com.sievex.crawler.entity.Site;

import java.util.List;

public interface SiteService {
    Site saveSite(Site site);

    List<Site> getAllSites();

    Site getSiteById(Long id);

    void deleteSiteById(Long id);

    Site updateSite(Site site);
}
