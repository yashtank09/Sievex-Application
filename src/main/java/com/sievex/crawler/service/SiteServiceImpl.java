package com.sievex.crawler.service;

import com.sievex.crawler.entity.Site;
import com.sievex.crawler.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;

    @Autowired
    SiteServiceImpl(SiteRepository theSiteRepository) {
        siteRepository = theSiteRepository;
    }

    @Override
    public Site saveSite(Site site) {
        return siteRepository.save(site);
    }

    @Override
    public Site findSiteByDomain(String domain) {
        return siteRepository.findByDomain(domain);
    }

    @Override
    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    @Override
    public Site getSiteById(Long id) {
        return siteRepository.findById(id).orElseThrow(() -> new RuntimeException("Site not found with id: " + id));
    }

    @Override
    public void deleteSiteById(Long id) {
        siteRepository.deleteById(id);
    }

    @Override
    public Site updateSite(Site site) {
        return siteRepository.save(site);
    }
}
