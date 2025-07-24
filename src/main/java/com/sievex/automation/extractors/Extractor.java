package com.sievex.automation.extractors;

import com.sievex.crawler.entity.Jobs;
import com.sievex.dto.ExtractionResult;

public interface Extractor {
    ExtractionResult extract(Jobs job);
    boolean supports(String domain);
}
