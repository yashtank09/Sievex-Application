package com.sievex.automation.core;

import com.sievex.automation.crawling.AmazonUkCrawlerImpl;
import com.sievex.automation.crawling.Crawler;
import com.sievex.crawler.entity.Jobs;
import com.sievex.crawler.enums.StatusTypeEnum;
import com.sievex.crawler.repository.JobRepository;
import com.sievex.crawler.repository.StatusTypeRepository;
import com.sievex.dto.CrawlResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JobExecutor {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutor.class);

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    private final JobRepository jobRepository;
    private final StatusTypeRepository statusTypeRepository;

    public JobExecutor(JobRepository jobRepository, StatusTypeRepository statusTypeRepository) {
        this.jobRepository = jobRepository;
        this.statusTypeRepository = statusTypeRepository;
    }

    public void executePendingJobs() {
        List<Jobs> pendingJobs = jobRepository.findTop5ByStatusAliasOrderByPriorityAscCreatedAtAsc(StatusTypeEnum.PENDING.getType());
        for (Jobs job : pendingJobs) {
            executor.submit(() -> {
                try {
                    // Process the job
                    processCrawlingJobs(job);
                } catch (Exception e) {
                    // Handle exceptions
                    logger.error("Error processing job " + job.getId() + ": " + e.getMessage());
                }
            });
        }

    }

    private void processCrawlingJobs(Jobs job) {
        try {
            job.setStatus(statusTypeRepository.findByAlias(StatusTypeEnum.CRAWLING_IN_PROGRESS.getType()));
            jobRepository.save(job);

            String crawlerClassname = job.getSite().getCrawlerClassName();
            Crawler crawler = resolveCrawler(crawlerClassname);

            CrawlResult crawlResult = crawler.crawl(job);
            if (crawlResult.isSuccess()) {
                job.setStatus(statusTypeRepository.findByAlias(StatusTypeEnum.CRAWLING_COMPLETED.getType()));
            } else {
                job.setStatus(statusTypeRepository.findByAlias(StatusTypeEnum.FAILED.getType()));
            }
        } catch (Exception e) {
            // Handle exceptions specific to job processing
            job.setStatus(statusTypeRepository.findByAlias(StatusTypeEnum.FAILED.getType()));
        } finally {
            jobRepository.save(job);
        }
    }

    private Crawler resolveCrawler(String crawlerClassname) {
        try {
            Class<?> clazz = Class.forName(crawlerClassname);
            return (Crawler) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            logger.error("Failed to instantiate crawler: {}",  crawlerClassname, e);
        }
        return null; // Fallback to a default crawler
    }
}
