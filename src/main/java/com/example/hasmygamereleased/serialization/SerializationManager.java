package com.example.hasmygamereleased.serialization;

import com.example.hasmygamereleased.models.all_apps.AppIdMap;
import com.example.hasmygamereleased.models.all_apps.SteamGameList;
import com.example.hasmygamereleased.models.blacklist.IdBlacklist;

/**
 * Manager of ObjectSerializer
 * 'SerializablePlaceHolder' type should be replaced by the Object to be Serialized that implements the Serializable interface
 * FilePath and FileName must be updated
 * All are marked with //TODO
 * <p>
 * Handling multiple ObjectSerializer's are possible
 */


public enum SerializationManager {

    INSTANCE;

    private final String pathToFileFolder = "src/main/resources/list/";

    private final String gameListFilename = "gameList";
    private final ObjectSerializer<SteamGameList> gameListSerializer =
            new ObjectSerializer<>(pathToFileFolder + gameListFilename);
    private SteamGameList gameList;


    private final String appIdMapFilename = "appIdMap";
    private final ObjectSerializer<AppIdMap> appIdMapSerializer =
            new ObjectSerializer<>(pathToFileFolder + appIdMapFilename);
    private AppIdMap appIdMap;


    private final String blackListFilename = "blackList";
    private final ObjectSerializer<IdBlacklist> blacklistSerializer =
            new ObjectSerializer<>(pathToFileFolder + blackListFilename);
    private IdBlacklist blacklist;


    SerializationManager() {
        initializeGameList();
        initializeAppIdMap();
        initializeBlackList();
    }

    //GameList methods
    private void initializeGameList() {
        try {
            gameList = gameListSerializer.load();
        } catch (Exception e) {
            gameListSerializer.save(new SteamGameList());
            gameList = gameListSerializer.load();
        }
    }
    public SteamGameList getList() {
        return gameList;
    }
    public void saveGameList(SteamGameList gameList) {
        gameListSerializer.save(gameList);
        this.gameList = gameListSerializer.load();
    }

    //AppIdMap Methods
    private void initializeAppIdMap() {
        try {
            appIdMap = appIdMapSerializer.load();
        } catch (Exception e) {
            appIdMapSerializer.save(new AppIdMap());
            appIdMap = appIdMapSerializer.load();
        }
    }
    public AppIdMap getAppIdMap() {
        return appIdMap;
    }
    public void saveAppIdMap(AppIdMap appIdMap) {
        appIdMapSerializer.save(appIdMap);
        this.appIdMap = appIdMapSerializer.load();
    }

    //Blacklist methods
    private void initializeBlackList() {
        try {
            blacklist = blacklistSerializer.load();
        } catch (Exception e) {
            blacklistSerializer.save(new IdBlacklist());
            blacklist = blacklistSerializer.load();
        }
    }
    public IdBlacklist getBlacklist(){
        return blacklist;
    }
    public void saveBlacklist(IdBlacklist blacklist) {
        blacklistSerializer.save(blacklist);
        this.blacklist = blacklistSerializer.load();
    }

    //Universal Methods
    public void saveAll() {
        gameListSerializer.save(gameList);
        appIdMapSerializer.save(appIdMap);
        blacklistSerializer.save(blacklist);
    }
}
