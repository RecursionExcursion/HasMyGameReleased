package com.example.hasmygamereleased.models.all_apps;

import com.example.hasmygamereleased.models.app.SteamApp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SteamGameList implements Serializable {

    private final List<SteamApp> gameList;

    public SteamGameList() {
        this.gameList = new ArrayList<>();
    }

    public List<SteamApp> getGameList() {
        return gameList;
    }

    public void addGame(SteamApp steamApp) {
        boolean alreadyPresent = gameList.stream().anyMatch(g -> g.getId()== steamApp.getId());
        if (!alreadyPresent) {
            gameList.add(steamApp);
        }
    }

    public boolean remove(SteamApp app){
        return gameList.remove(app);
    }
}
