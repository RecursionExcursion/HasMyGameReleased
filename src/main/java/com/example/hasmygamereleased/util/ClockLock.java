package com.example.hasmygamereleased.util;

import java.time.LocalDateTime;

public class ClockLock {

    private LocalDateTime unlockTime;
    private boolean isLocked = false;
    private final int lockDurationInMinutes;

    public ClockLock(int lockDurationInMinutes) {
        this.lockDurationInMinutes = lockDurationInMinutes;
    }

    public boolean isLocked(LocalDateTime now) {
        if(unlockTime != null){
            isLocked = now.isBefore(unlockTime);
        }
        return isLocked;
    }

    public void lock(LocalDateTime now) {
        unlockTime = now.plusMinutes(lockDurationInMinutes);
        isLocked = true;
    }
}
