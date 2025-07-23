package com.sievex.automation.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JobAutomationService {

    private final CrawlingExecutor jobExecutor;
    private final ExtractorExecutor extractorExecutor;

    @Autowired
    public JobAutomationService(CrawlingExecutor jobExecutor, ExtractorExecutor extractorExecutor) {
        this.jobExecutor = jobExecutor;
        this.extractorExecutor = extractorExecutor;
    }

    @Scheduled(fixedRate = 10000) // Run every minute
    public void runJobAutomation() {
        jobExecutor.executePendingJobs();
        extractorExecutor.executePendingJobs();
    }
}
