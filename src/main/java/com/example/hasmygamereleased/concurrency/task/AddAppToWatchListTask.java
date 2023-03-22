package com.example.hasmygamereleased.concurrency.task;

import com.example.hasmygamereleased.models.app.SteamApp;
import com.example.hasmygamereleased.repository.SteamDataInterface;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.Map;

public class AddAppToWatchListTask extends Task<SteamApp> {

    private final Map.Entry<Long, String> appMapEntry;
    private final ObservableList<SteamApp> items;

    public AddAppToWatchListTask(Map.Entry<Long, String> appMapEntry, ObservableList<SteamApp> items) {
        this.appMapEntry = appMapEntry;
        this.items = items;
    }

    @Override
    protected SteamApp call() {
        SteamApp appById = new SteamDataInterface().getAppById(appMapEntry.getKey());
        items.add(appById);
        System.out.println("Added: " + appMapEntry.getValue());
        return appById;
    }
}
