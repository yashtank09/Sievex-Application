package com.sievex.crawler.controller;

import com.sievex.constants.ApiResponseConstants;
import com.sievex.crawler.entity.SiteData;
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
    private SiteDataResponseDto toDto(SiteData siteData) {
        SiteDataResponseDto dto = new SiteDataResponseDto();
        dto.setSiteId(siteData.getSiteId());
        dto.setSiteName(siteData.getSiteName());
        dto.setSiteTypeName(siteData.getSiteType().getTypeName()); // safely access
        dto.setSiteDomain(siteData.getSiteDomain());
        dto.setSiteUrl(siteData.getSiteUrl());
        dto.setSiteDescription(siteData.getSiteDescription());
        dto.setSiteCategory(siteData.getSiteCategory());
        dto.setSiteCountry(siteData.getSiteCountry());
        dto.setSiteStatus(siteData.getSiteStatus());
        return dto;
    }

    @GetMapping("/all")
    public ResponseEntity<DataApiResponse<List<SiteDataResponseDto>>> getAllSites() {
        List<SiteData> siteDataList = siteService.getAllSites();
        logger.debug("Site data list size: {}", siteDataList.size());
        if (siteDataList.isEmpty()) {
            logger.error("No site data found");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_NOT_FOUND, ApiResponseConstants.MSG_READ_NOT_FOUND), HttpStatus.NOT_MODIFIED);
        }
        logger.info("List of sites retrieved successfully");
        List<SiteDataResponseDto> dtoList = siteDataList.stream().map(this::toDto).toList();
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, dtoList), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DataApiResponse<List<SiteDataResponseDto>>> getAllSites(@PathVariable Long id) {
        List<SiteData> siteDataList = new ArrayList<>();
        siteDataList.add(siteService.getSiteById(id));
        List<SiteDataResponseDto> dtoList = siteDataList.stream().map(this::toDto).toList();
        logger.info("Site data retrieved successfully");
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, dtoList), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<DataApiResponse<SiteDataResponseDto>> addSite(@RequestBody SiteData siteData) {
        SiteData siteDataResponse = siteService.saveSite(siteData);
        if (siteDataResponse == null) {
            logger.error("Failed to create site data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_CREATE_FAILED), HttpStatus.BAD_REQUEST);
        }
        SiteDataResponseDto siteDataResponseDto = toDto(siteDataResponse);
        logger.info("Site data created successfully");
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_CREATE_SUCCESS, siteDataResponseDto), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<DataApiResponse<SiteDataResponseDto>> updateSite(@RequestBody SiteData siteData) {
        SiteData updatedSiteData = siteService.updateSite(siteData);
        if (updatedSiteData == null) {
            logger.error("Failed to update site data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_UPDATE_FAILED), HttpStatus.BAD_REQUEST);
        }
        SiteDataResponseDto updatedSiteDataResponse = toDto(updatedSiteData);
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
