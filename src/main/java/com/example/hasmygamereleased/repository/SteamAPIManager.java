package com.example.hasmygamereleased.repository;

import com.example.hasmygamereleased.models.all_apps.AppIdMap;
import com.example.hasmygamereleased.models.app.AppDataDto;
import com.example.hasmygamereleased.repository.api.APICaller;
import com.example.hasmygamereleased.repository.api.dto.WrapperDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

class SteamAPIManager {

    private final String appIdsUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?format=json";
    private final String appDataUrl = " https://store.steampowered.com/api/appdetails?appids=" /* + appId */;

    private final ObjectMapper mapper = new ObjectMapper();
    private final APICaller apiCaller = new APICaller();

    AppIdMap buildAppIdMap() {
        AppIdMap map = new AppIdMap();
        WrapperDto.AppListDto.AppDto[] apps = getAppIdsFromUrlAsDto().getAppList().getApps();
        for (WrapperDto.AppListDto.AppDto app : apps) {
            map.putIfAbsent(app.getAppId(), app.getName());
        }
        return map;
    }

    private WrapperDto getAppIdsFromUrlAsDto() {
        WrapperDto dto;
        try {
            String response = apiCaller.call(appIdsUrl, null);
            dto = mapper.readValue(response, WrapperDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dto;
    }


    AppDataDto getGameWithId(long id) {
        AppDataDto app;
        try {
            String url = appDataUrl + id;
            String response = apiCaller.call(url, null);
            app = mapper.readValue(response, AppDataDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return app;
    }
}

