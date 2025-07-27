package com.sievex.crawler.service;

import com.sievex.crawler.entity.JobType;

import java.util.List;

public interface JobTypeService {
    JobType getJobTypeByAlias(String alias);
    List<JobType> getAllJobTypes();
    JobType saveJobType(JobType jobType);
    JobType updateJobType(JobType jobType);
    boolean deleteJobType(Byte jobType);
}
