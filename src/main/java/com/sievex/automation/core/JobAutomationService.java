package com.sievex.automation.core;

import com.sievex.crawler.repository.JobRepository;
import com.sievex.crawler.repository.SiteRepository;

public class JobAutomationService {

    private final JobRepository jobRepository;
    private final SiteRepository siteRepository;

    public JobAutomationService(JobRepository jobRepository, SiteRepository siteRepository) {
        this.jobRepository = jobRepository;
        this.siteRepository = siteRepository;
    }

    public void processPendingJobs() {

    }
}
