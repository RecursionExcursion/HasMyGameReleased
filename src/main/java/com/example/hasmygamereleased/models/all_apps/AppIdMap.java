package com.example.hasmygamereleased.models.all_apps;

import java.io.Serializable;
import java.util.*;

public class AppIdMap implements Serializable {

    private final Map<Long, String> gameIdmap = new HashMap<>();

    public Map<Long, String> getGameIdmap() {
        return Collections.unmodifiableMap(gameIdmap);
    }

    public void put(long key, String val) {
        gameIdmap.put(key, val);
    }

    public void putIfAbsent(long key, String val) {
        gameIdmap.putIfAbsent(key, val);
    }

    public String get(long key) {
        return gameIdmap.get(key);
    }

    public Set<Long> keySet() {
        return gameIdmap.keySet();
    }

    public Collection<String> values() {
        return gameIdmap.values();
    }

    public Set<Map.Entry<Long, String>> entrySet(){
       return gameIdmap.entrySet();
    }
}
