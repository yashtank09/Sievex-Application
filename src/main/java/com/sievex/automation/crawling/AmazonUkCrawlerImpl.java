package com.sievex.automation.crawling;

import com.sievex.automation.core.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

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
    public void crawl(String job) {

    }

    @Override
    public boolean supports(String domain) {
        return false;
    }
}
