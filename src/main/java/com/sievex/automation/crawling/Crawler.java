package com.sievex.automation.crawling;

public interface Crawler {
    public void crawl(String job);
    public boolean supports(String domain);
}
