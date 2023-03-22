package com.example.hasmygamereleased.repository.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class APICaller {

    public APICaller() {
    }

    public String call(String urlString, String bearer) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        if (bearer != null) {
            conn.setRequestProperty("Authorization", "Bearer " + bearer);
        }

        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("GET");
        conn.connect();

        int respCode = conn.getResponseCode();

        if (respCode != 200) {
            throw new RuntimeException("Http Response Code- " + respCode);
        } else {
            /*
            TODO Non-stream version
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Scanner scanner = new Scanner(br);
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }
            br.close();
            return sb.toString();
             */
            return new BufferedReader(new InputStreamReader(conn.getInputStream())).lines()
                                                                                   .collect(Collectors.joining());
        }
    }
}
