package com.sievex.crawler.controller;

import com.sievex.constants.ApiResponseConstants;
import com.sievex.crawler.entity.SiteType;
import com.sievex.crawler.service.SiteTypeService;
import com.sievex.dto.DataApiResponse;
import com.sievex.dto.request.SiteTypeRequestDto;
import com.sievex.dto.response.SiteTypeResponseDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(SiteTypeController.BASE_URL)
public class SiteTypeController {
    public static final String BASE_URL = "site-type";
    private static final Logger logger = LoggerFactory.getLogger(SiteTypeController.class);

    private final SiteTypeService siteTypeService;

    @Autowired
    public SiteTypeController(SiteTypeService siteTypeService) {
        this.siteTypeService = siteTypeService;
    }

    private SiteType toRequestEntity(SiteTypeRequestDto siteTypeRequestDto) {
        SiteType entity = new SiteType();
        if (siteTypeRequestDto == null) {
            return null;
        }
        entity.setName(siteTypeRequestDto.getName());
        entity.setAlias(siteTypeRequestDto.getAlias());
        entity.setDescription(siteTypeRequestDto.getDescription());
        entity.setStatus(siteTypeRequestDto.getStatus());
        return entity;
    }

    private SiteTypeResponseDto toResponseEntity(SiteType jobType) {
        SiteTypeResponseDto entity = new SiteTypeResponseDto();
        entity.setName(jobType.getName());
        entity.setAlias(jobType.getAlias());
        entity.setDescription(jobType.getDescription());
        entity.setStatus(jobType.getStatus());
        return entity;
    }

    @GetMapping("/get/all-site-type")
    public ResponseEntity<DataApiResponse<List<SiteTypeResponseDto>>> getAllSiteType() {
        List<SiteType> jobTypes = siteTypeService.getAllSiteTypes();
        if (jobTypes.isEmpty()) {
            logger.error("No job type data found");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_NOT_FOUND, ApiResponseConstants.MSG_READ_NOT_FOUND), HttpStatus.NOT_MODIFIED);
        }
        List<SiteTypeResponseDto> dtoList = jobTypes.stream().map(this::toResponseEntity).toList();
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, dtoList), HttpStatus.OK);
    }

    @GetMapping("/get/{alias}")
    public ResponseEntity<DataApiResponse<SiteTypeResponseDto>> getSiteTypeByAlias(@PathVariable("alias") String alias) {
        SiteType siteTypes = siteTypeService.getSiteTypeByAlias(alias);
        if (siteTypes == null) {
            logger.error("No Site type data found");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_NOT_FOUND, ApiResponseConstants.MSG_READ_NOT_FOUND), HttpStatus.NOT_MODIFIED);
        }
        SiteTypeResponseDto siteTypeResponseDto = toResponseEntity(siteTypes);
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, siteTypeResponseDto), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<DataApiResponse<SiteTypeResponseDto>> createSiteType(@Valid @RequestBody SiteTypeRequestDto SiteType) {
        SiteType createdSiteType = siteTypeService.saveSiteType(toRequestEntity(SiteType));
        if (createdSiteType == null) {
            logger.error("Failed to create Site type data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_CREATE_FAILED), HttpStatus.BAD_REQUEST);
        }
        SiteTypeResponseDto createdSiteTypeResponse = toResponseEntity(createdSiteType);
        logger.info("Site type data created successfully");
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_CREATE_SUCCESS, createdSiteTypeResponse), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<DataApiResponse<SiteTypeResponseDto>> updateSiteType(@RequestBody SiteTypeRequestDto SiteType) {
        SiteType updatedSiteType = siteTypeService.updateSiteType(toRequestEntity(SiteType));
        if (updatedSiteType == null) {
            logger.error("Failed to update Site type data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_UPDATE_FAILED), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_UPDATE_SUCCESS), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DataApiResponse<Void>> deleteSiteTypeById(@PathVariable Byte id) {
        boolean isDeleted = siteTypeService.deleteSiteType(id);
        if (!isDeleted) {
            logger.error("Failed to delete Site type data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_DELETE_FAILED), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_DELETE_SUCCESS), HttpStatus.OK);
    }
}
