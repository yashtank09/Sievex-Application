package com.sievex.crawler.controller;

import com.sievex.constants.ApiResponseConstants;
import com.sievex.crawler.entity.Site;
import com.sievex.crawler.service.SiteService;
import com.sievex.dto.DataApiResponse;
import com.sievex.dto.SiteDataResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = SiteController.BASE_URL)
public class SiteController {

    public static final String BASE_URL = "site";
    private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

    // Autowire the SiteService
    private final SiteService siteService;

    public SiteController(SiteService theSiteService) {
        siteService = theSiteService;
    }

    // DTO Mapper
    private SiteDataResponseDto toDto(Site site) {
        SiteDataResponseDto dto = new SiteDataResponseDto();
        dto.setSiteId(site.getId());
        dto.setSiteName(site.getName());
        dto.setSiteTypeName(site.getSiteType().getName()); // safely access
        dto.setSiteDomain(site.getDomain());
        dto.setSiteUrl(site.getUrl());
        dto.setSiteDescription(site.getDescription());
        dto.setSiteCategory(site.getCategory());
        dto.setSiteCountry(site.getCountry());
        dto.setSiteStatus(site.getStatus());
        return dto;
    }

    @GetMapping("/all")
    public ResponseEntity<DataApiResponse<List<SiteDataResponseDto>>> getAllSites() {
        List<Site> siteList = siteService.getAllSites();
        logger.debug("Site data list size: {}", siteList.size());
        if (siteList.isEmpty()) {
            logger.error("No site data found");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_NOT_FOUND, ApiResponseConstants.MSG_READ_NOT_FOUND), HttpStatus.NOT_MODIFIED);
        }
        logger.info("List of sites retrieved successfully");
        List<SiteDataResponseDto> dtoList = siteList.stream().map(this::toDto).toList();
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, dtoList), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DataApiResponse<List<SiteDataResponseDto>>> getAllSites(@PathVariable Long id) {
        List<Site> siteList = new ArrayList<>();
        siteList.add(siteService.getSiteById(id));
        List<SiteDataResponseDto> dtoList = siteList.stream().map(this::toDto).toList();
        logger.info("Site data retrieved successfully");
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, dtoList), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<DataApiResponse<SiteDataResponseDto>> addSite(@RequestBody Site site) {
        Site siteResponse = siteService.saveSite(site);
        if (siteResponse == null) {
            logger.error("Failed to create site data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_CREATE_FAILED), HttpStatus.BAD_REQUEST);
        }
        SiteDataResponseDto siteDataResponseDto = toDto(siteResponse);
        logger.info("Site data created successfully");
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_CREATE_SUCCESS, siteDataResponseDto), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<DataApiResponse<SiteDataResponseDto>> updateSite(@RequestBody Site site) {
        Site updatedSite = siteService.updateSite(site);
        if (updatedSite == null) {
            logger.error("Failed to update site data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_UPDATE_FAILED), HttpStatus.BAD_REQUEST);
        }
        SiteDataResponseDto updatedSiteDataResponse = toDto(updatedSite);
        logger.info("Site data updated successfully");
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_UPDATE_SUCCESS, updatedSiteDataResponse), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DataApiResponse<Void>> deleteSite(@PathVariable Long id) {
        siteService.deleteSiteById(id);
        logger.debug("Site data deleted successfully");
        if (siteService.getSiteById(id) != null) {
            logger.error("Failed to delete site data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_DELETE_FAILED), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_DELETE_SUCCESS), HttpStatus.OK);
    }
}
