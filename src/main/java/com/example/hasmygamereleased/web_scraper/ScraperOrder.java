package com.example.hasmygamereleased.web_scraper;

public abstract class ScraperOrder {

    protected String urlString;
    protected String[] htmlLineMarkers;

    public ScraperOrder(String urlString) {
        this.urlString = urlString;
    }

    /**
     * Initializes 'htmlLineMarkers' Array with values;
     */
    protected void loadMarkerArray(String... lineMarkers) {
        htmlLineMarkers = lineMarkers;
    }

    public String getUrlString() {
        return urlString;
    }

    public String[] getHtmlLineMarkers() {
        if (htmlLineMarkers != null) {
            return htmlLineMarkers;
        }
        throw new IllegalStateException("LineMarkerArray has not been initialized");
    }
}
