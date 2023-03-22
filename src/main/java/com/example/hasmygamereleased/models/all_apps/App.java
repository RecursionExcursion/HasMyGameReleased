package com.example.hasmygamereleased.models.all_apps;

import java.io.Serializable;

public class App implements Serializable {

    private final String title;
    private final Long id;

    public App(String title, Long id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }
}
