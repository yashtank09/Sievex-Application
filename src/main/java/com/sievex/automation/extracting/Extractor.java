package com.sievex.automation.extracting;

import com.sievex.crawler.entity.Jobs;
import com.sievex.dto.ExtractionResult;

public interface Extractor {
    ExtractionResult extract(Jobs job);
    boolean supports(String domain);
}
