package com.sievex.crawler.controller;

import com.sievex.constants.ApiResponseConstants;
import com.sievex.crawler.entity.Site;
import com.sievex.crawler.service.SiteService;
import com.sievex.crawler.service.SiteTypeService;
import com.sievex.dto.DataApiResponse;
import com.sievex.dto.request.SiteRequestDto;
import com.sievex.dto.response.SiteResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = SiteController.BASE_URL)
@Tag(name = "Site Management", description = "APIs for managing sites in the crawler system")
public class SiteController {

    public static final String BASE_URL = "site";
    private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

    // Autowire the SiteService
    private final SiteService siteService;
    private final SiteTypeService siteTypeService;

    @Autowired
    public SiteController(SiteService theSiteService, SiteTypeService siteTypeService) {
        siteService = theSiteService;
        this.siteTypeService = siteTypeService;
    }

    // DTO Mapper
    private SiteResponseDto toResponseEntity(Site site) {
        SiteResponseDto dto = new SiteResponseDto();
        if (site == null) {
            return null;
        }
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

    private Site toRequestEntity(SiteRequestDto siteDto) {
        Site entity = new Site();
        if (siteDto == null) {
            return null;
        }
        entity.setName(siteDto.getName());
        entity.setSiteType(siteTypeService.getSiteTypeByAlias(siteDto.getSiteType()));
        entity.setDomain(siteDto.getDomain());
        entity.setUrl(siteDto.getUrl());
        entity.setDescription(siteDto.getDescription());
        entity.setCategory(siteDto.getCategory());
        entity.setCountry(siteDto.getCountry());
        entity.setStatus(siteDto.getStatus());
        return entity;
    }

    @Operation(
        summary = "Get all sites",
        description = "Retrieves a list of all sites in the system with their details"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Sites retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DataApiResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "304",
            description = "No sites found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DataApiResponse.class)
            )
        )
    })
    @GetMapping("/all")
    public ResponseEntity<DataApiResponse<List<SiteResponseDto>>> getAllSites() {
        List<Site> siteList = siteService.getAllSites();
        logger.debug("Site data list size: {}", siteList.size());
        if (siteList.isEmpty()) {
            logger.error("No site data found");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_NOT_FOUND, ApiResponseConstants.MSG_READ_NOT_FOUND), HttpStatus.NOT_MODIFIED);
        }
        logger.info("List of sites retrieved successfully");
        List<SiteResponseDto> dtoList = siteList.stream().map(this::toResponseEntity).toList();
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, dtoList), HttpStatus.OK);
    }

    @Operation(
        summary = "Get site by ID",
        description = "Retrieves a specific site by its unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Site retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DataApiResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Site not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DataApiResponse.class)
            )
        )
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<DataApiResponse<List<SiteResponseDto>>> getAllSites(
        @Parameter(description = "Unique identifier of the site", required = true, example = "1")
        @PathVariable Long id) {
        List<Site> siteList = new ArrayList<>();
        siteList.add(siteService.getSiteById(id));
        List<SiteResponseDto> dtoList = siteList.stream().map(this::toResponseEntity).toList();
        logger.info("Site data retrieved successfully");
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, dtoList), HttpStatus.OK);
    }

    @Operation(
        summary = "Add new site",
        description = "Creates a new site in the system with the provided details"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Site created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DataApiResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid site data or creation failed",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DataApiResponse.class)
            )
        )
    })
    @PostMapping("/add")
    public ResponseEntity<DataApiResponse<SiteResponseDto>> addSite(
        @Parameter(description = "Site details to be created", required = true)
        @Valid @RequestBody SiteRequestDto site) {
        Site siteResponse = siteService.saveSite(toRequestEntity(site));
        if (siteResponse == null) {
            logger.error("Failed to create site data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_CREATE_FAILED), HttpStatus.BAD_REQUEST);
        }
        SiteResponseDto siteResponseDto = toResponseEntity(siteResponse);
        logger.info("Site data created successfully");
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_CREATE_SUCCESS, siteResponseDto), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update existing site",
        description = "Updates an existing site with new information"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Site updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DataApiResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid site data or update failed",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DataApiResponse.class)
            )
        )
    })
    @PutMapping("/update")
    public ResponseEntity<DataApiResponse<SiteResponseDto>> updateSite(
        @Parameter(description = "Updated site details", required = true)
        @RequestBody SiteRequestDto site) {
        Site updatedSite = siteService.updateSite(toRequestEntity(site));
        if (updatedSite == null) {
            logger.error("Failed to update site data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_UPDATE_FAILED), HttpStatus.BAD_REQUEST);
        }
        SiteResponseDto updatedSiteDataResponse = toResponseEntity(updatedSite);
        logger.info("Site data updated successfully");
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_UPDATE_SUCCESS, updatedSiteDataResponse), HttpStatus.OK);
    }

    @Operation(
        summary = "Delete site",
        description = "Removes a site from the system by its unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Site deleted successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DataApiResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Site deletion failed",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DataApiResponse.class)
            )
        )
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DataApiResponse<Void>> deleteSite(
        @Parameter(description = "Unique identifier of the site to delete", required = true, example = "1")
        @PathVariable Long id) {
        siteService.deleteSiteById(id);
        logger.debug("Site data deleted successfully");
        if (siteService.getSiteById(id) != null) {
            logger.error("Failed to delete site data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_DELETE_FAILED), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_DELETE_SUCCESS), HttpStatus.OK);
    }
}
