package com.example.hasmygamereleased.repository;

import com.example.hasmygamereleased.models.all_apps.AppIdMap;
import com.example.hasmygamereleased.models.app.SteamApp;

class SteamRepo {

    SteamAPIManager apiManager = new SteamAPIManager();

    AppIdMap getAppIdMap() {
        return apiManager.buildAppIdMap();
    }


    SteamApp getAppById(long id) {
        return apiManager.getGameWithId(id)
                         .tryMapToApp()
                         .orElseThrow();
    }
}

