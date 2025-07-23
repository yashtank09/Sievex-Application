package com.sievex.automation.extracting;

import com.sievex.automation.core.BaseClass;
import com.sievex.crawler.entity.Jobs;
import com.sievex.dto.ExtractionResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AmazonUkExtractorImpl extends BaseClass implements Extractor{

    private static final Logger logger = LoggerFactory.getLogger(AmazonUkExtractorImpl.class);

    @Override
    public ExtractionResult extract(Jobs job) {
        try {
            String pageSourcePath = job.getPageSourcePath();
            if (pageSourcePath == null || pageSourcePath.isEmpty()) {
                logger.error("Page source path is not defined for job: {}", job.getId());
                return new ExtractionResult(false, "Page source path is not defined", null, Instant.now());
            }

            logger.info("Page source path: {}", pageSourcePath);
            Document doc = Jsoup.parse(pageSourcePath);
            String savedJsonPath = saveProductDataToJson(job.getId(), List.of(extractProductDetails(doc, job.getUrl())), "amazon-uk");
            if (savedJsonPath != null && !savedJsonPath.isEmpty()) {
                logger.info("Product data saved to: {}", savedJsonPath);
                return new ExtractionResult(true, "Extraction successful", savedJsonPath, Instant.now());
            } else {
                logger.error("Failed to save extracted product data.");
            }
        } catch (Exception e) {
            logger.error("Error during extraction: {}", e.getMessage());
        }
        return null;
    }

    private Map<String, String> extractProductDetails(Document doc, String jobUrl) {
        Map<String, String> product = new HashMap<>();
        product.put("url", jobUrl);
        product.put("title", doc.selectXpath("//div[@id='ppd']//h1[@id='title']").text());
        product.put("rating", doc.selectXpath("//div[@id='averageCustomerReviews_feature_div']//span[contains(@class, 'reviewCountTextLinkedHistogram')]//i[contains(@class,'a-icon-star')]").text());
        product.put("price", doc.selectXpath("//div[@id='corePriceDisplay_desktop_feature_div']//span[contains(@class, 'reinventPricePriceToPayMargin')]").text());
        product.put("brand", doc.selectXpath("//div[@id='productOverview_feature_div']//tr[contains(@class, 'po-brand')]//span[contains(@class, 'po-break-word')]").text());
        product.put("manufacturer", doc.selectXpath("//div[@id='productDetails_feature_div']//th[contains(@class, 'prodDetSectionEntry') and contains(text(), 'Manufacturer')]/following-sibling::td[contains(@class, 'prodDetAttrValue')]").text());
        product.put("asin", doc.selectXpath("//div[@id='productDetails_feature_div']//th[contains(@class, 'prodDetSectionEntry') and contains(text(), 'ASIN')]/following-sibling::td[contains(@class, 'prodDetAttrValue')]").text());

        return product;
    }

    @Override
    public boolean supports(String domain) {
        return domain != null && domain.contains("amazon.co.uk");
    }
}
