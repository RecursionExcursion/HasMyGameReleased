package com.example.hasmygamereleased.web_scraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

class WebScraper {

    private final ScraperOrder order;

    WebScraper(ScraperOrder order) {
        this.order = order;
    }

    String[] run() {
        HttpURLConnection conn = openHttpConnection();
        return parseConnectionStream(conn);
    }

    private HttpURLConnection openHttpConnection() {
        HttpURLConnection connection;
        try {
            URL url = new URL(order.getUrlString());
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Chrome");
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                System.out.println(connection.getResponseMessage());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    private String[] parseConnectionStream(HttpURLConnection connection) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return order.htmlLineMarkers.length == 0 ?
                    br.lines().toArray(String[]::new) :
                    br.lines().filter(l -> Arrays.stream(order.htmlLineMarkers)
                                                 .anyMatch(l::contains))
                      .toArray(String[]::new);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}
