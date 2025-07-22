package com.sievex.automation.core;

import com.sievex.automation.crawling.Crawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DefaultCrawlerFactory implements CrawlerFactory {

    private final Map<String, Crawler> crawlerMap = new HashMap<>();

    public DefaultCrawlerFactory(List<Crawler> crawlers) {
        // Register default crawlers here if needed
        // Example: crawlerMap.put("AmazonCrawler", new AmazonCrawler());
        for (Crawler crawler : crawlers) {
            crawlerMap.put(crawler.getClass().getName(), crawler);
        }
    }


    @Override
    public Crawler getCrawler(String className) {
       return crawlerMap.get(className);
    }
}
