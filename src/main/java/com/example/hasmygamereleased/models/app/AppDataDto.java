package com.example.hasmygamereleased.models.app;

import com.example.hasmygamereleased.serialization.SerializationManager;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AppDataDto {

    @JsonAnySetter
    @JsonAnyGetter
    private Map<String, Object> dynamicValues = new HashMap<>();

    public Map<String, Object> getDynamicValues() {
        return dynamicValues;
    }

    public void setDynamicValues(Map<String, Object> dynamicValues) {
        this.dynamicValues = dynamicValues;
    }

    public Optional<SteamApp> tryMapToApp() {

        try {
            return Optional.ofNullable(mapToApp());
        } catch (NullPointerException e) {
            System.out.println("Exception Caught- Object has null id reference");
            return Optional.empty();
        }
    }

    private SteamApp mapToApp() throws NullPointerException {

        Map<String, Object> dataMap = extractAppData();

        //Name
        String title = (String) dataMap.getOrDefault("name", null);

        //Id
        Integer id = (Integer) dataMap.getOrDefault("steam_appid", null);

        //Release Date
        Map<String, Object> releaseDateMap = (Map<String, Object>) dataMap.getOrDefault("release_date", null);
        String date = (String) releaseDateMap.getOrDefault("date", null);
        SteamApp app = null;
        if (date != null) {
            app = new SteamApp();
            app.setTitle(title);
            app.setReleaseDate(date);
            app.setId(id);
            app.setUrl("https://store.steampowered.com/app/" + id);
        }
        return app;
    }

    private Map<String, Object> extractAppData() {
        Optional<String> first = this.getDynamicValues().keySet().stream().findFirst();
        String id = first.orElseThrow();
        try {
            Map<String, Object> responseMap = (Map<String, Object>) this.getDynamicValues().get(first.orElseThrow());
            Map<String, Object> data = (Map<String, Object>) responseMap.getOrDefault("data", null);
            if (data == null) throw new IllegalStateException("Data cannot be null");
            return data;
        } catch (Exception e) {
            System.out.format("Exception caught- App:%s has no data associated with it.\n", id);
            System.out.format("Blacklisting app:%s\n", id);
            SerializationManager.INSTANCE.getBlacklist().getBlacklistedIds().add(Long.parseLong(id));
        }
        return null;
    }
}
