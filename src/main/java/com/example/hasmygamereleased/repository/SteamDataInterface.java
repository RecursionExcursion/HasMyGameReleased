package com.example.hasmygamereleased.repository;

import com.example.hasmygamereleased.models.all_apps.AppIdMap;
import com.example.hasmygamereleased.models.all_apps.SteamGameList;
import com.example.hasmygamereleased.models.app.SteamApp;
import com.example.hasmygamereleased.serialization.SerializationManager;

public class SteamDataInterface {

    SerializationManager serializationManager = SerializationManager.INSTANCE;
    SteamRepo steamRepo = new SteamRepo();


    public AppIdMap getAppIdMap() {
        AppIdMap map = serializationManager.getAppIdMap();
        if (map.keySet().isEmpty()) {
            map = steamRepo.getAppIdMap();
            serializationManager.saveAppIdMap(map);
        }
        return map;
    }

    public SteamApp getAppById(long id) {
        SteamApp app = steamRepo.getAppById(id);
        addSteamAppToList(app);
        return app;
    }

    public SteamGameList getGameList(){
        return serializationManager.getList();
    }

    public void addSteamAppToList(SteamApp app){
        serializationManager.getList().addGame(app);
    }

    public void removeSteamAppFromList(SteamApp app) {
        serializationManager.getList().remove(app);
    }
}
