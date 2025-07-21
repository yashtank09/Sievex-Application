package com.sievex.automation.crawling;

import com.sievex.crawler.entity.Jobs;
import com.sievex.dto.CrawlResult;

public interface Crawler {
    public CrawlResult crawl(Jobs job);
    public boolean supports(String domain);
}
