package com.sievex.automation.crawlers;

import com.sievex.crawler.entity.Jobs;
import com.sievex.dto.CrawlResult;

public interface Crawler {
    CrawlResult crawl(Jobs job);
    boolean supports(String domain);
}
