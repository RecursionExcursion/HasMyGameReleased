package com.example.hasmygamereleased.models.blacklist;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class IdBlacklist implements Serializable {

    private final Set<Long> blacklistedIds = new HashSet<>();

    public Set<Long> getBlacklistedIds() {
        return blacklistedIds;
    }
}
