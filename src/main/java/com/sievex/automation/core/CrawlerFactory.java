package com.sievex.automation.core;

import com.sievex.automation.crawling.Crawler;

public interface CrawlerFactory {
    Crawler getCrawler(String className);
}
