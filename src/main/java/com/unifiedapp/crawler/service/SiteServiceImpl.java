package com.unifiedapp.crawler.service;

import com.unifiedapp.crawler.entity.SiteData;
import com.unifiedapp.crawler.repository.SiteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;

    SiteServiceImpl(SiteRepository theSiteRepository) {
        siteRepository = theSiteRepository;
    }

    @Override
    public SiteData saveSite(SiteData siteData) {
        return siteRepository.save(siteData);
    }

    @Override
    public List<SiteData> getAllSites() {
        return siteRepository.findAll();
    }

    @Override
    public SiteData getSiteById(Long id) {
        return siteRepository.findById(id).orElseThrow(() -> new RuntimeException("Site not found with id: " + id));
    }

    @Override
    public void deleteSiteById(Long id) {
        siteRepository.deleteById(id);
    }

    @Override
    public SiteData updateSite(SiteData siteData) {
        return siteRepository.save(siteData);
    }
}
