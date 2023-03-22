package com.example.hasmygamereleased.web_scraper;

public abstract class ScraperManager {

    public Record processOrder(ScraperOrder order) throws IllegalStateException{
        String[] results = new WebScraper(order).run();
        if (results.length == 0) {
            throw new IllegalStateException("No results from webpage");
        }

        Record result = buildResult(results, order);
        return result;
    }

    protected abstract Record buildResult(String[] data, ScraperOrder order);
}
