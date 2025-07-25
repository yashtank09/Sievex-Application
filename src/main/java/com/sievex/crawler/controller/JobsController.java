package com.sievex.crawler.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.sievex.constants.ApiResponseConstants;
import com.sievex.crawler.entity.Jobs;
import com.sievex.crawler.repository.JobTypeRepository;
import com.sievex.crawler.repository.SiteRepository;
import com.sievex.crawler.repository.SiteTypeRepository;
import com.sievex.crawler.service.JobsService;
import com.sievex.dto.DataApiResponse;
import com.sievex.dto.request.JobsRequestDto;
import com.sievex.dto.response.JobsResponseDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(JobsController.BASE_URL)
public class JobsController {
    public static final String BASE_URL = "jobs";
    private static final Logger logger = LoggerFactory.getLogger(JobsController.class);

    private final JobsService jobsService;
    private final SiteRepository siteRepository;
    private final SiteTypeRepository siteTypeRepository;
    private final JobTypeRepository jobTypeRepository;

    @Autowired
    public JobsController(JobsService jobsService, SiteTypeRepository siteTypeRepository, SiteRepository siteRepository, JobTypeRepository jobTypeRepository) {
        this.jobsService = jobsService;
        this.siteTypeRepository = siteTypeRepository;
        this.siteRepository = siteRepository;
        this.jobTypeRepository = jobTypeRepository;
    }

    private Jobs toRequestEntity(JobsRequestDto jobDto) {
        Jobs entity = new Jobs();
        if (jobDto == null) {
            return null;
        }
        entity.setName(jobDto.getName());
        entity.setUrl(jobDto.getUrl());
        entity.setDescription(jobDto.getDescription());
        entity.setJobTypeId(jobTypeRepository.findByAlias(jobDto.getJobType()));
        entity.setPriority(jobDto.getPriority());
        return entity;
    }

    private JobsResponseDto toResponseEntity(Jobs jobs) {
        JobsResponseDto entity = new JobsResponseDto();
        entity.setName(jobs.getName());
        entity.setUrl(jobs.getUrl());
        entity.setDomain(jobs.getSite().getDomain());
        entity.setJobType(jobs.getSite().getName());
        entity.setStatus(jobs.getStatus().getName());
        entity.setDescription(jobs.getDescription());
        return entity;
    }


    @GetMapping("/all")
    public ResponseEntity<DataApiResponse<List<JobsResponseDto>>> getAllJobs() {
        List<Jobs> jobsList = jobsService.getAllJobs();
        if (jobsList.isEmpty()) {
            logger.error("No job data found");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_NOT_FOUND, ApiResponseConstants.MSG_READ_NOT_FOUND), HttpStatus.NOT_MODIFIED);
        }
        List<JobsResponseDto> dtoList = jobsList.stream().map(job -> {
            JobsResponseDto dto = new JobsResponseDto();
            dto.setName(job.getName());
            dto.setJobType(job.getSite().getName());
            dto.setStatus(job.getStatus().getName());
            dto.setDescription(job.getDescription());
            return dto;
        }).toList();
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, dtoList), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<DataApiResponse<List<JobsResponseDto>>> createJob(@Valid @RequestBody List<JobsRequestDto> jobsRequest) {
        List<Jobs> createdJobs = jobsService.saveJobs(jobsRequest.stream().map(this::toRequestEntity).toList());
        if (createdJobs.isEmpty()) {
            logger.error("Failed to create job data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_CREATE_FAILED), HttpStatus.BAD_REQUEST);
        }
        List<JobsResponseDto> createdJobsResponse = createdJobs.stream().map(this::toResponseEntity).toList();
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, createdJobsResponse), HttpStatus.OK);
    }
}
