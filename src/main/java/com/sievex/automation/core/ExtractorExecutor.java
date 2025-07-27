package com.sievex.automation.core;

import com.sievex.automation.extractors.Extractor;
import com.sievex.crawler.entity.Jobs;
import com.sievex.crawler.enums.StatusTypeEnum;
import com.sievex.crawler.service.JobsService;
import com.sievex.crawler.service.StatusTypeService;
import com.sievex.dto.ExtractionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ExtractorExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ExtractorExecutor.class);

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    private final JobsService jobsService;
    private final StatusTypeService statusTypeService;
    private final ExtractorFactory extractorFactory;

    @Autowired
    public ExtractorExecutor(JobsService jobsService, StatusTypeService statusTypeService, ExtractorFactory extractorFactory) {
        this.jobsService = jobsService;
        this.statusTypeService = statusTypeService;
        this.extractorFactory = extractorFactory;
    }

    public void executePendingJobs() {
        logger.info("Executing pending extractor jobs...");
        List<Jobs> pendingJobs = jobsService.findTop5PendingJobs(StatusTypeEnum.CRAWLING_COMPLETED.getType());
        if (pendingJobs == null || pendingJobs.isEmpty()) {
            logger.info("No pending extractor jobs found to execute.");
            return;
        }
        for (Jobs job : pendingJobs) {
            executor.submit(() -> {
                try {
                    // Process the job
                    processExtractorJobs(job);
                } catch (Exception e) {
                    // Handle exceptions
                    logger.error("Error processing extractor job {} : {}", job.getId(), e.getMessage(), e);
                }
            });
        }
    }

    private void processExtractorJobs(Jobs job) {
        try {
            job.setStatus(statusTypeService.getStatusTypeByAlias(StatusTypeEnum.EXTRACTING_IN_PROGRESS.getType()));
            jobsService.saveJob(job);
            logger.info("Processing extractor job: {}", job.getId());

            // Get the extractor class name from the job
            String extractorClassName = job.getSite().getExtractorClassName();
            if (extractorClassName == null || extractorClassName.isEmpty()) {
                logger.error("Extractor class name is not set for job {}", job.getId());
                return;
            }

            // Get the extractor instance
            Extractor extractor = extractorFactory.getExtractor(extractorClassName);
            if (extractor == null) {
                logger.error("No extractor found for class name: {}", extractorClassName);
                return;
            }

            logger.info("Processing extractor job: {} with job status {}", job.getId(), job.getStatus().getName());
            logger.info("Extractor class: {}", extractor.getClass().getName());
            if (!extractor.supports(job.getSite().getDomain())) {
                logger.error("Extractor {} does not support domain {}", extractor.getClass().getName(), job.getSite().getDomain());
                return;
            }
            // Execute the extraction process
            ExtractionResult extractionResult = extractor.extract(job);
            logger.info("Extraction result: {}", extractionResult.getDataFilePath());
            if (extractionResult.isSuccess()) {
                job.setStatus(statusTypeService.getStatusTypeByAlias(StatusTypeEnum.EXTRACTING_COMPLETED.getType()));
                logger.info("Extraction completed successfully for job {}", job.getId());
            } else {
                job.setStatus(statusTypeService.getStatusTypeByAlias(StatusTypeEnum.EXTRACTING_FAILED.getType()));
                logger.error("Extraction failed for job {}: {}", job.getId(), extractionResult.getMessage());
            }
        } catch (Exception e) {
            // Handle exceptions
            logger.error("Error processing extractor job {} : {}", job.getId(), e.getMessage(), e);
            job.setStatus(statusTypeService.getStatusTypeByAlias(StatusTypeEnum.EXTRACTING_FAILED.getType()));
        } finally {
            // Update job status to completed
            jobsService.saveJob(job);
            logger.info("Extractor job {} completed successfully.", job.getId());
        }
    }
}
