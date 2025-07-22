package com.sievex.automation.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JobAutomationService {

    private final JobExecutor jobExecutor;

    @Autowired
    public JobAutomationService(JobExecutor jobExecutor) {
        this.jobExecutor = jobExecutor;
    }

    @Scheduled(fixedRate = 10000) // Run every minute
    public void runJobAutomation() {
        jobExecutor.executePendingJobs();
    }
}
