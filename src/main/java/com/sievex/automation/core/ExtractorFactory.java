package com.sievex.automation.core;

import com.sievex.automation.extractors.Extractor;

public interface ExtractorFactory {
    Extractor getExtractor(String className);
}
