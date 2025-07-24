package com.sievex.crawler.controller;

import com.sievex.constants.ApiResponseConstants;
import com.sievex.crawler.entity.Jobs;
import com.sievex.crawler.service.JobsService;
import com.sievex.dto.DataApiResponse;
import com.sievex.dto.JobsDataResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<DataApiResponse<List<JobsDataResponseDto>>> getAllJobs() {
        List<Jobs> jobsList = jobsService.getAllJobs();
        if (jobsList.isEmpty()) {
            logger.error("No job data found");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_NOT_FOUND, ApiResponseConstants.MSG_READ_NOT_FOUND), HttpStatus.NOT_MODIFIED);
        }
        List<JobsDataResponseDto> dtoList = jobsList.stream().map(job -> {
            JobsDataResponseDto dto = new JobsDataResponseDto();
            dto.setJobId(job.getId());
            dto.setJobName(job.getName());
            dto.setJobType(job.getSite().getName());
            dto.setJobStatus(job.getStatus().getName());
            dto.setJobDescription(job.getDescription());
            dto.setJobCreatedAt(String.valueOf(job.getCreatedAt()));
            dto.setJobUpdatedAt(String.valueOf(job.getUpdatedAt()));
            return dto;
        }).toList();
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, dtoList), HttpStatus.OK);
    }

}
