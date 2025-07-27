package com.sievex.crawler.controller;

import com.sievex.constants.ApiResponseConstants;
import com.sievex.crawler.entity.JobType;
import com.sievex.crawler.service.JobTypeService;
import com.sievex.dto.DataApiResponse;
import com.sievex.dto.request.JobTypeRequestDto;
import com.sievex.dto.response.JobTypeResponseDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(JobTypeController.BASE_URL)
public class JobTypeController {
    public static final String BASE_URL = "job-type";
    private static final Logger logger = LoggerFactory.getLogger(JobTypeController.class);

    private final JobTypeService jobTypeService;

    @Autowired
    public JobTypeController(JobTypeService jobTypeService) {
        this.jobTypeService = jobTypeService;
    }

    private JobType toRequestEntity(JobTypeRequestDto jobTypeRequestDto) {
        JobType entity = new JobType();
        if (jobTypeRequestDto == null) {
            return null;
        }
        entity.setName(jobTypeRequestDto.getName());
        entity.setAlias(jobTypeRequestDto.getAlias());
        entity.setDescription(jobTypeRequestDto.getDescription());
        entity.setStatus(jobTypeRequestDto.getStatus());
        return entity;
    }

    private JobTypeResponseDto toResponseEntity(JobType jobType) {
        JobTypeResponseDto entity = new JobTypeResponseDto();
        entity.setName(jobType.getName());
        entity.setAlias(jobType.getAlias());
        entity.setDescription(jobType.getDescription());
        entity.setStatus(jobType.getStatus());
        return entity;
    }

    @GetMapping("/get/all-job-type")
    public ResponseEntity<DataApiResponse<List<JobTypeResponseDto>>> getAllJobType() {
        List<JobType> jobTypes = jobTypeService.getAllJobTypes();
        if (jobTypes.isEmpty()) {
            logger.error("No job type data found");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_NOT_FOUND, ApiResponseConstants.MSG_READ_NOT_FOUND), HttpStatus.NOT_MODIFIED);
        }
        List<JobTypeResponseDto> dtoList = jobTypes.stream().map(this::toResponseEntity).toList();
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, dtoList), HttpStatus.OK);
    }

    @GetMapping("/get/{alias}")
    public ResponseEntity<DataApiResponse<JobTypeResponseDto>> getJobTypeByAlias(String alias) {
        JobType jobTypes = jobTypeService.getJobTypeByAlias(alias);
        if (jobTypes == null) {
            logger.error("No job type data found");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_NOT_FOUND, ApiResponseConstants.MSG_READ_NOT_FOUND), HttpStatus.NOT_MODIFIED);
        }
        JobTypeResponseDto jobTypeResponseDto = toResponseEntity(jobTypes);
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, jobTypeResponseDto), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<DataApiResponse<JobTypeResponseDto>> createJobType(@Valid @RequestBody JobTypeRequestDto jobType) {
        JobType createdJobType = jobTypeService.saveJobType(toRequestEntity(jobType));
        if (createdJobType == null) {
            logger.error("Failed to create job type data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_CREATE_FAILED), HttpStatus.BAD_REQUEST);
        }
        JobTypeResponseDto createdJobTypeResponse = toResponseEntity(createdJobType);
        logger.info("Job type data created successfully");
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_CREATE_SUCCESS, createdJobTypeResponse), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<DataApiResponse<JobTypeResponseDto>> updateJobType(@RequestBody JobTypeRequestDto jobType) {
        JobType updatedJobType = jobTypeService.updateJobType(toRequestEntity(jobType));
        if (updatedJobType == null) {
            logger.error("Failed to update job type data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_UPDATE_FAILED), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_UPDATE_SUCCESS), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DataApiResponse<Void>> deleteJobTypeById(@PathVariable Byte id) {
        boolean isDeleted = jobTypeService.deleteJobType(id);
        if (!isDeleted) {
            logger.error("Failed to delete job type data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_DELETE_FAILED), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_DELETE_SUCCESS), HttpStatus.OK);
    }

}
