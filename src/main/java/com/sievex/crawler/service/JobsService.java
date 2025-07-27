package com.sievex.crawler.service;

import com.sievex.crawler.entity.Jobs;
import com.sievex.crawler.entity.StatusType;

import java.util.List;

public interface JobsService {
    Jobs saveJob(Jobs theJob);

    List<Jobs> saveJobs(List<Jobs> theJobs);

    List<Jobs> getAllJobs();

    List<Jobs> getPendingJobs(String statusAlias);

    List<Jobs> updateJobs(List<Jobs> list);

    boolean deleteJobById(Long id);

    List<Jobs> findTop5PendingJobs(String statusAlias);
}
