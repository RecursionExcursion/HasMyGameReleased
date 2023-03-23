package com.example.hasmygamereleased.concurrency.task;

import com.example.hasmygamereleased.models.all_apps.SteamGameList;
import com.example.hasmygamereleased.models.app.SteamApp;
import com.example.hasmygamereleased.repository.SteamDataInterface;
import javafx.concurrent.Task;

import java.util.List;

public class SaveWatchListTask extends Task<Object> {

    private final List<SteamApp> applist;

    public SaveWatchListTask(List<SteamApp> applist) {
        this.applist = applist;
    }

    @Override
    protected Object call() {
        SteamGameList steamGameList = new SteamGameList();
        applist.forEach(steamGameList::addGame);
        new SteamDataInterface().overwriteGameList(steamGameList);
        return null;
    }
}
