package com.example.hasmygamereleased.scene;

import java.net.URL;

public enum CssManager {

    INSTANCE;

    //TODO Css paths
    private final String cssPath = "/css/app.css";

    public String getCssUrl() {
        URL cssUrl = getClass().getResource(cssPath);
        return returnStringOrExit(cssUrl);
    }

    private static String returnStringOrExit(URL cssUrl) {
        if (cssUrl == null) {
            System.out.println("Resource not found. Aborting application.");
            System.exit(-1);
        }
        return cssUrl.toExternalForm();
    }
}
