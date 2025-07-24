package com.sievex.automation.core;

import com.sievex.automation.crawlers.Crawler;

public interface CrawlerFactory {
    Crawler getCrawler(String className);
}
