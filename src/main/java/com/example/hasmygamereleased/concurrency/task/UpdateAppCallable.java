package com.example.hasmygamereleased.concurrency.task;

import com.example.hasmygamereleased.models.app.SteamApp;
import com.example.hasmygamereleased.repository.SteamDataInterface;

import java.util.concurrent.Callable;

public class UpdateAppCallable implements Callable<SteamApp> {

    private final SteamApp steamApp;

    public UpdateAppCallable(SteamApp steamApp) {
        this.steamApp = steamApp;
    }

    @Override
    public SteamApp call() throws Exception {
        return new SteamDataInterface().getAppById(steamApp.getId());
    }
}
