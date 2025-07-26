package com.sievex.crawler.service;

import com.sievex.crawler.entity.Jobs;
import com.sievex.crawler.entity.StatusType;
import com.sievex.crawler.repository.JobRepository;
import com.sievex.dto.response.JobsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobsServiceImpl implements JobsService {

    private final JobRepository jobRepository;

    @Autowired
    JobsServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Jobs saveJob(Jobs theJob) {
        return jobRepository.save(theJob);
    }

    @Override
    public List<Jobs> saveJobs(List<Jobs> jobsList) {
        return jobRepository.saveAll(jobsList);
    }

    @Override
    public List<Jobs> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public List<Jobs> getPendingJobs(StatusType statusAlias) {
        return jobRepository.findPendingJobs(statusAlias);
    }

    @Override
    public List<JobsResponseDto> updateJobs(List<Jobs> list) {
        return List.of();
    }
}
