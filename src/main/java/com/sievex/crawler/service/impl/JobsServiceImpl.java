package com.sievex.crawler.service.impl;

import com.sievex.crawler.entity.Jobs;
import com.sievex.crawler.repository.JobRepository;
import com.sievex.crawler.service.JobsService;
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
    public List<Jobs> getPendingJobs(String statusAlias) {
        return jobRepository.findPendingJobs(statusAlias);
    }

    @Override
    public List<Jobs> updateJobs(List<Jobs> list) {

        return jobRepository.saveAll(list);
    }

    @Override
    public boolean deleteJobById(Long id) {
        jobRepository.deleteById(id);
        return !jobRepository.existsById(id);
    }

    @Override
    public List<Jobs> findTop5PendingJobs(String statusAlias) {
        return jobRepository.findTop5ByStatusAliasOrderByPriorityAscCreatedAtAsc(statusAlias);
    }
}
