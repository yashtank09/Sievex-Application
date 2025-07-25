package com.sievex.crawler.controller;

import com.sievex.constants.ApiResponseConstants;
import com.sievex.crawler.entity.Jobs;
import com.sievex.crawler.service.JobsService;
import com.sievex.dto.DataApiResponse;
import com.sievex.dto.request.JobsRequestDto;
import com.sievex.dto.response.JobsResponseDto;
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

    @Autowired
    public JobsController(JobsService jobsService) {
        this.jobsService = jobsService;
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
            dto.setJobCreatedAt(String.valueOf(job.getCreatedAt()));
            dto.setJobUpdatedAt(String.valueOf(job.getUpdatedAt()));
            return dto;
        }).toList();
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, dtoList), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<DataApiResponse<List<JobsResponseDto>>> createJob(@RequestBody List<JobsRequestDto> jobsRequest) {
        List<JobsResponseDto> createdJobs = null;
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, createdJobs), HttpStatus.OK);
    }
}
