package com.sievex.crawler.service;

import com.sievex.crawler.entity.Jobs;
import com.sievex.crawler.entity.StatusType;

import java.util.List;

public interface JobsService {
    Jobs saveJob(Jobs theJob);

    List<Jobs> saveJobs(List<Jobs> theJobs);

    List<Jobs> getAllJobs();

    List<Jobs> getPendingJobs(StatusType statusAlias);
}
