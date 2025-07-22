package com.sievex.automation.crawling;

import com.sievex.automation.core.BaseClass;
import com.sievex.crawler.entity.Jobs;
import com.sievex.dto.CrawlResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component
public class AmazonUkCrawlerImpl extends BaseClass implements Crawler {

    private static final Logger logger = LoggerFactory.getLogger(AmazonUkCrawlerImpl.class);

    public static void clickCookiePopup(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#sp-cc-accept"))).click();
            logger.info("Popup clicked!");
        } catch (Exception e) {
            logger.error("No popup detected!");
        }
    }

    @Override
    public CrawlResult crawl(Jobs job) {
        try {
            String url = job.getUrl();
            Document doc = Jsoup.connect(url).get();
            String html = doc.outerHtml();

            String filePath = saveHtmlToFile(job.getId(), html, "amazon-uk");
            if (filePath != null && !filePath.isEmpty()) {
                job.setPageSourcePath(filePath);
                logger.info("Page source saved to: {}", job.getPageSourcePath());
                return new CrawlResult(true, "Crawling successful", filePath, Instant.now());
            } else {
                logger.error("Failed to save page source.");
            }
        } catch (IOException e) {
            logger.error("Error during crawling: {}", e.getMessage());
            return new CrawlResult(false, "Crawling failed: " + e.getMessage(), null, Instant.now());
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage());
            return new CrawlResult(false, "Unexpected error: " + e.getMessage(), null, Instant.now());
        }
        return new CrawlResult();
    }

    @Override
    public boolean supports(String domain) {
        return domain != null && domain.contains("amazon.co.uk");
    }
}
