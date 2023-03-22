package com.example.hasmygamereleased.web_scraper;

public class SteamOrder extends ScraperOrder {

    private final String nameMark = "class=\"apphub_AppName\"";
    private final String dateMark = "class=\"date\"";

    public SteamOrder(String urlString) {
        super(urlString);
        loadMarkerArray(nameMark, dateMark);
    }

    public String getNameMark() {
        return nameMark;
    }

    public String getDateMark() {
        return dateMark;
    }
}
