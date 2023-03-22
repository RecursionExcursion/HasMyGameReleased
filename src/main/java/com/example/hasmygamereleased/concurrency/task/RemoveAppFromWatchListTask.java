package com.example.hasmygamereleased.concurrency.task;

import com.example.hasmygamereleased.models.all_apps.SteamGameList;
import com.example.hasmygamereleased.models.app.SteamApp;
import com.example.hasmygamereleased.repository.SteamDataInterface;
import javafx.concurrent.Task;

public class RemoveAppFromWatchListTask extends Task<SteamGameList> {

    private final SteamApp app;

    public RemoveAppFromWatchListTask(SteamApp app) {
        this.app = app;
    }

    @Override
    protected SteamGameList call() {
        new SteamDataInterface().removeSteamAppFromList(app);
        System.out.println(app.getTitle() + " has been deleted");
        return null;
    }
}
