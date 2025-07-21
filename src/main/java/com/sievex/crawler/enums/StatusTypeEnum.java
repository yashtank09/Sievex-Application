package com.sievex.crawler.enums;

public enum StatusTypeEnum {
    PENDING("PENDING"),
    CRAWLING_IN_PROGRESS("CRAWLING_IN_PROGRESS"),
    CRAWLING_COMPLETED("CRAWLING_COMPLETED"),
    CRAWLING_FAILED("CRAWLING_COMPLETED"),
    EXTRACTING_IN_PROGRESS("EXTRACTING_IN_PROGRESS"),
    EXTRACTING_COMPLETED("EXTRACTING_COMPLETED"),
    EXTRACTING_FAILED("EXTRACTING_FAILED"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED");

    private final String alias;

    StatusTypeEnum(String alias) {
        this.alias = alias;
    }

    public String getType() {
        return alias;
    }

    @Override
    public String toString() {
        return alias;
    }
}
