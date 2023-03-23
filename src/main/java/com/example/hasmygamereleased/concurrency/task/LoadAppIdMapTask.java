package com.example.hasmygamereleased.concurrency.task;

import com.example.hasmygamereleased.models.all_apps.AppIdMap;
import com.example.hasmygamereleased.repository.SteamDataInterface;
import javafx.concurrent.Task;

public class LoadAppIdMapTask extends Task<AppIdMap> {

    @Override
    protected AppIdMap call() {
        return new SteamDataInterface().getAppIdMap();
    }
}