package com.sievex.automation.core;

import com.sievex.automation.crawlers.Crawler;
import com.sievex.automation.extractors.Extractor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CoreAutomationFactory implements CrawlerFactory, ExtractorFactory {

    private final Map<String, Crawler> crawlerMap = new HashMap<>();
    private final Map<String, Extractor> extractorMap = new HashMap<>();

    public CoreAutomationFactory(List<Crawler> crawlers, List<Extractor> extractors) {
        // Register default crawlers here if needed
        // Example: crawlerMap.put("AmazonCrawler", new AmazonCrawler());
        for (Crawler crawler : crawlers) {
            crawlerMap.put(crawler.getClass().getName(), crawler);
        }

        // Register default extractors here if needed
        // Example: extractorMap.put("ProductExtractor", new ProductExtractor());
        for (Extractor extractor: extractors) {
            extractorMap.put(extractor.getClass().getName(), extractor);
        }
    }


    @Override
    public Crawler getCrawler(String className) {
       return crawlerMap.get(className);
    }

    @Override
    public Extractor getExtractor(String className) {
        return extractorMap.get(className);
    }
}
