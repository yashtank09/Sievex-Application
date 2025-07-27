package com.sievex.automation.core;

import com.sievex.automation.crawlers.Crawler;
import com.sievex.crawler.entity.Jobs;
import com.sievex.crawler.enums.StatusTypeEnum;
import com.sievex.crawler.service.JobsService;
import com.sievex.crawler.service.StatusTypeService;
import com.sievex.dto.CrawlResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CrawlingExecutor {

    private static final Logger logger = LoggerFactory.getLogger(CrawlingExecutor.class);

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    private final JobsService jobsService;
    private final StatusTypeService statusTypeService;
    private final CrawlerFactory crawlerFactory;

    @Autowired
    public CrawlingExecutor(JobsService jobsService, StatusTypeService statusTypeService, CrawlerFactory crawlerFactory) {
        this.jobsService = jobsService;
        this.statusTypeService = statusTypeService;
        this.crawlerFactory = crawlerFactory;
    }

    public void executePendingJobs() {
        List<Jobs> pendingJobs = jobsService.findTop5PendingJobs(StatusTypeEnum.PENDING.getType());
        if (pendingJobs == null || pendingJobs.isEmpty()) {
            logger.info("No pending jobs found to execute.");
            return;
        }
        for (Jobs job : pendingJobs) {
            executor.submit(() -> {
                try {
                    // Process the job
                    processCrawlingJobs(job);
                } catch (Exception e) {
                    // Handle exceptions
                    logger.error("Error processing job {} : {}", job.getId(), e.getMessage());
                }
            });
        }

    }

    private void processCrawlingJobs(Jobs job) {
        try {
            job.setStatus(statusTypeService.getStatusTypeByAlias(StatusTypeEnum.CRAWLING_IN_PROGRESS.getType()));
            jobsService.saveJob(job);
            logger.info("Processing job: {}", job.getId());

            String domain = job.getSite().getDomain();
            logger.info("Resolving crawler for domain: {}", domain);

            String crawlerClassname = job.getSite().getCrawlerClassName();
            if (crawlerClassname == null || crawlerClassname.isEmpty()) {
                logger.error("No crawler class specified for job: {}", job.getId());
                job.setStatus(statusTypeService.getStatusTypeByAlias(StatusTypeEnum.CRAWLING_FAILED.getType()));
                jobsService.saveJob(job);
                return;
            }

            logger.info("Using crawler class: {}", crawlerClassname);
            Crawler crawler = crawlerFactory.getCrawler(crawlerClassname);

            if (crawler == null) {
                logger.error("Failed to resolve crawler for job: {}", job.getId());
                job.setStatus(statusTypeService.getStatusTypeByAlias(StatusTypeEnum.CRAWLING_FAILED.getType()));
                jobsService.saveJob(job);
                return;
            }

            logger.info("Crawling job: {}", job.getId());
            logger.info("Crawling with crawler: {}", crawler.getClass().getName());

            if (!crawler.supports(job.getSite().getDomain())) {
                logger.error("Crawler does not support the domain for job: {}", job.getId());
                job.setStatus(statusTypeService.getStatusTypeByAlias(StatusTypeEnum.CRAWLING_FAILED.getType()));
                jobsService.saveJob(job);
                return;
            }

            CrawlResult crawlResult = crawler.crawl(job);
            logger.info("Crawl result for job {}: {}", job.getId(), crawlResult);
            if (crawlResult.isSuccess()) {
                job.setPageSourcePath(crawlResult.getPageSourcePath());
                job.setStatus(statusTypeService.getStatusTypeByAlias(StatusTypeEnum.CRAWLING_COMPLETED.getType()));
            } else {
                logger.error("Crawling failed for job {}: {}", job.getId(), crawlResult.getMessage());
                job.setStatus(statusTypeService.getStatusTypeByAlias(StatusTypeEnum.CRAWLING_FAILED.getType()));
            }
        } catch (Exception e) {
            // Handle exceptions specific to job processing
            logger.error("Error processing job {}: {}", job.getId(), e.getMessage());
            job.setStatus(statusTypeService.getStatusTypeByAlias(StatusTypeEnum.CRAWLING_FAILED.getType()));
        } finally {
            jobsService.saveJob(job);
            logger.info("Crawling job {} completed successfully.", job.getId());
        }
    }

}
