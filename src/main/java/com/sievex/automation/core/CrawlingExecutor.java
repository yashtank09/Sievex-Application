package com.sievex.automation.core;

import com.sievex.automation.crawlers.Crawler;
import com.sievex.crawler.entity.Jobs;
import com.sievex.crawler.enums.StatusTypeEnum;
import com.sievex.crawler.repository.JobRepository;
import com.sievex.crawler.repository.StatusTypeRepository;
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

    private final JobRepository jobRepository;
    private final StatusTypeRepository statusTypeRepository;
    private final CrawlerFactory crawlerFactory;

    @Autowired
    public CrawlingExecutor(JobRepository jobRepository, StatusTypeRepository statusTypeRepository, CrawlerFactory crawlerFactory) {
        this.jobRepository = jobRepository;
        this.statusTypeRepository = statusTypeRepository;
        this.crawlerFactory = crawlerFactory;
    }

    public void executePendingJobs() {
        List<Jobs> pendingJobs = jobRepository.findTop5ByStatusAliasOrderByPriorityAscCreatedAtAsc(StatusTypeEnum.PENDING.getType());
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
            job.setStatus(statusTypeRepository.findByAlias(StatusTypeEnum.CRAWLING_IN_PROGRESS.getType()));
            jobRepository.save(job);
            logger.info("Processing job: {}", job.getId());

            String domain = job.getSite().getDomain();
            logger.info("Resolving crawler for domain: {}", domain);

            String crawlerClassname = job.getSite().getCrawlerClassName();
            if (crawlerClassname == null || crawlerClassname.isEmpty()) {
                logger.error("No crawler class specified for job: {}", job.getId());
                job.setStatus(statusTypeRepository.findByAlias(StatusTypeEnum.CRAWLING_FAILED.getType()));
                jobRepository.save(job);
                return;
            }

            logger.info("Using crawler class: {}", crawlerClassname);
            Crawler crawler = crawlerFactory.getCrawler(crawlerClassname);

            if (crawler == null) {
                logger.error("Failed to resolve crawler for job: {}", job.getId());
                job.setStatus(statusTypeRepository.findByAlias(StatusTypeEnum.CRAWLING_FAILED.getType()));
                jobRepository.save(job);
                return;
            }

            logger.info("Crawling job: {}", job.getId());
            logger.info("Crawling with crawler: {}", crawler.getClass().getName());

            if (!crawler.supports(job.getSite().getDomain())) {
                logger.error("Crawler does not support the domain for job: {}", job.getId());
                job.setStatus(statusTypeRepository.findByAlias(StatusTypeEnum.CRAWLING_FAILED.getType()));
                jobRepository.save(job);
                return;
            }

            CrawlResult crawlResult = crawler.crawl(job);
            logger.info("Crawl result for job {}: {}", job.getId(), crawlResult);
            if (crawlResult.isSuccess()) {
                job.setPageSourcePath(crawlResult.getPageSourcePath());
                job.setStatus(statusTypeRepository.findByAlias(StatusTypeEnum.CRAWLING_COMPLETED.getType()));
            } else {
                logger.error("Crawling failed for job {}: {}", job.getId(), crawlResult.getMessage());
                job.setStatus(statusTypeRepository.findByAlias(StatusTypeEnum.CRAWLING_FAILED.getType()));
            }
        } catch (Exception e) {
            // Handle exceptions specific to job processing
            logger.error("Error processing job {}: {}", job.getId(), e.getMessage());
            job.setStatus(statusTypeRepository.findByAlias(StatusTypeEnum.CRAWLING_FAILED.getType()));
        } finally {
            jobRepository.save(job);
            logger.info("Crawling job {} completed successfully.", job.getId());
        }
    }

}
