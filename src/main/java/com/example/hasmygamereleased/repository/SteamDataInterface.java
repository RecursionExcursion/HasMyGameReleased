package com.example.hasmygamereleased.repository;

import com.example.hasmygamereleased.models.all_apps.AppIdMap;
import com.example.hasmygamereleased.models.all_apps.SteamGameList;
import com.example.hasmygamereleased.models.app.SteamApp;
import com.example.hasmygamereleased.serialization.SerializationManager;

public class SteamDataInterface {

    private static final SerializationManager SERIALIZATION_MANAGER = SerializationManager.INSTANCE;
    private static final SteamRepo STEAM_REPO = new SteamRepo();
    
    public AppIdMap getAppIdMap() {
        AppIdMap map = SERIALIZATION_MANAGER.getAppIdMap();
        if (map.keySet().isEmpty()) {
            map = STEAM_REPO.getAppIdMap();
            SERIALIZATION_MANAGER.saveAppIdMap(map);
        }
        return map;
    }

    public SteamApp getAppById(long id) {
        SteamApp app = STEAM_REPO.getAppById(id);
        addSteamAppToList(app);
        return app;
    }

    public SteamGameList getGameList(){
        return SERIALIZATION_MANAGER.getList();
    }

    public void addSteamAppToList(SteamApp app){
        SERIALIZATION_MANAGER.getList().addGame(app);
    }

    public void removeSteamAppFromList(SteamApp app) {
        SERIALIZATION_MANAGER.getList().remove(app);
    }
}
