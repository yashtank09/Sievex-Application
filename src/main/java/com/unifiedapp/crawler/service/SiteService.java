package com.unifiedapp.crawler.service;
import com.unifiedapp.crawler.entity.SiteData;

import java.util.List;

public interface SiteService {
    SiteData saveSite(SiteData siteData);

    List<SiteData> getAllSites();

    SiteData getSiteById(Long id);

    void deleteSiteById(Long id);

    SiteData updateSite(SiteData siteData);
}
