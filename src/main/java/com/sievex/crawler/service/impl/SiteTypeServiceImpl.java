package com.sievex.crawler.service.impl;

import com.sievex.crawler.entity.SiteType;
import com.sievex.crawler.repository.SiteTypeRepository;
import com.sievex.crawler.service.SiteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteTypeServiceImpl implements SiteTypeService {

    private final SiteTypeRepository siteTypeRepository;

    @Autowired
    public SiteTypeServiceImpl(SiteTypeRepository siteTypeRepository) {
        this.siteTypeRepository = siteTypeRepository;
    }

    @Override
    public SiteType getSiteTypeByAlias(String alias) {
        return siteTypeRepository.findByAlias(alias);
    }

    @Override
    public List<SiteType> getAllSiteTypes() {
        return siteTypeRepository.findAll();
    }

    @Override
    public SiteType saveSiteType(SiteType siteType) {
        return siteTypeRepository.save(siteType);
    }

    @Override
    public SiteType updateSiteType(SiteType siteType) {
        return siteTypeRepository.save(siteType);
    }

    @Override
    public boolean deleteSiteType(Byte siteType) {
        siteTypeRepository.deleteById(siteType);
        return siteTypeRepository.existsById(siteType);
    }
}
