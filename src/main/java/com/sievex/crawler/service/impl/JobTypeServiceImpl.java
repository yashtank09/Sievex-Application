package com.sievex.crawler.service.impl;

import com.sievex.crawler.entity.JobType;
import com.sievex.crawler.repository.JobTypeRepository;
import com.sievex.crawler.service.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobTypeServiceImpl implements JobTypeService {

    private final JobTypeRepository jobTypeRepository;

    @Autowired
    public JobTypeServiceImpl(JobTypeRepository jobTypeRepository) {
        this.jobTypeRepository = jobTypeRepository;
    }

    @Override
    public JobType getJobTypeByAlias(String alias) {
        return jobTypeRepository.findByAlias(alias);
    }

    @Override
    public List<JobType> getAllJobTypes() {
        return jobTypeRepository.findAll();
    }

    @Override
    public JobType saveJobType(JobType jobType) {
        return jobTypeRepository.save(jobType);
    }

    @Override
    public JobType updateJobType(JobType jobType) {
        return jobTypeRepository.save(jobType);
    }

    @Override
    public boolean deleteJobType(Byte jobTypeId) {
        jobTypeRepository.deleteById(jobTypeId);
        return !jobTypeRepository.existsById(jobTypeId);
    }
}
