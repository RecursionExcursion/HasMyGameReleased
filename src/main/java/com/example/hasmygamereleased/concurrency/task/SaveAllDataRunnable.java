package com.example.hasmygamereleased.concurrency.task;

import com.example.hasmygamereleased.serialization.SerializationManager;

public class SaveAllDataRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Saving all serializable data to local memory");
        SerializationManager.INSTANCE.saveAll();
    }
}
