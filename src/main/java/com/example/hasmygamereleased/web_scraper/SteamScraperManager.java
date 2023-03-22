package com.example.hasmygamereleased.web_scraper;

public class SteamScraperManager extends ScraperManager {

    @Override
    protected Record buildResult(String[] data, ScraperOrder order) {
        String date = null;
        String name = null;

        for (String line : data) {
            String subString = line.split(">")[1].split("<")[0];
            if (line.contains(((SteamOrder) order).getDateMark())) date = subString;
            if (line.contains(((SteamOrder) order).getNameMark())) name = subString;
        }

        return new SteamRecord(order.urlString, name, date);
    }
}
