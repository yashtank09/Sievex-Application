package com.sievex.crawler.service;

import com.sievex.crawler.entity.SiteType;

import java.util.List;

public interface SiteTypeService {
    SiteType getSiteTypeByAlias(String alias);
    List<SiteType> getAllSiteTypes();
    SiteType saveSiteType(SiteType siteType);
    SiteType updateSiteType(SiteType siteType);
    boolean deleteSiteType(Byte siteType);
}
