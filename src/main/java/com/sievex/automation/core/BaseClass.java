package com.sievex.automation.core;

import com.sievex.automation.util.PropertyLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public abstract class BaseClass {

    private static final Logger logger = LoggerFactory.getLogger(BaseClass.class);

    // property variables
    private final WebDriver driver;
    boolean headless;
    protected BaseClass() {
        this.headless = PropertyLoader.getBoolean("headless", true);
        this.driver = initializeWebDriver();
    }

    private WebDriver initializeWebDriver() {
        try {
            logger.info("Initializing WebDriver");
            ChromeOptions options = new ChromeOptions();
            List<String> arguments = Arrays.asList(
                    "--disable-gpu",
                    "--no-sandbox",
                    "--disable-dev-shm-usage"
            );
            options.addArguments(arguments);
            if (headless) {
                options.addArguments("--headless");
            }
            return new ChromeDriver(options);
        } catch (Exception e) {
            logger.error("Error initializing WebDriver: {}", e.getMessage());
        }
        return null;
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected void quitDriver() {
        if (this.driver != null) {
            try {
                driver.quit();
                logger.info("WebDriver quit successfully.");
            } catch (Exception e) {
                logger.error("Error while quitting WebDriver", e);
            }
        }
    }

    protected String saveHtmlToFile(Long jobId, String html, String filePrefix) throws IOException {
        Path path = Paths.get("data_resource/outer_htmls/" + filePrefix +"_"+ jobId + ".html");
        Files.createDirectories(path.getParent());
        Files.writeString(path, html, StandardCharsets.UTF_8);
        return path.toString();
    }
}
