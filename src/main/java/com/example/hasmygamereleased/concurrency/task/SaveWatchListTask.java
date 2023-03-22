package com.example.hasmygamereleased.concurrency.task;

import com.example.hasmygamereleased.models.all_apps.SteamGameList;
import javafx.concurrent.Task;

public class SaveWatchListTask extends Task<Boolean> {

    private final SteamGameList gameList;

    public SaveWatchListTask(SteamGameList gameList) {
        this.gameList = gameList;
    }

    @Override
    protected Boolean call() {
        try {
//            new FooInterface().saveGameList(gameList);
//            SerializationManager.INSTANCE.saveGameList(gameList);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
