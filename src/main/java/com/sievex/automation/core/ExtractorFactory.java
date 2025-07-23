package com.sievex.automation.core;

import com.sievex.automation.extracting.Extractor;

public interface ExtractorFactory {
    Extractor getExtractor(String className);
}
