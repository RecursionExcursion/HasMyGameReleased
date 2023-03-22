package com.example.hasmygamereleased.concurrency.task;

import com.example.hasmygamereleased.models.app.SteamApp;
import com.example.hasmygamereleased.repository.SteamDataInterface;
import com.example.hasmygamereleased.util.DTFormatter;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

public class LoadGameListCallable implements Callable<List<SteamApp>> {
    @Override
    public List<SteamApp> call() {
        List<SteamApp> gameList = new SteamDataInterface().getGameList().getGameList();
        gameList.sort(Comparator.comparing(steamApp -> DTFormatter.format(steamApp.getReleaseDate())));
        return gameList;
    }
}
