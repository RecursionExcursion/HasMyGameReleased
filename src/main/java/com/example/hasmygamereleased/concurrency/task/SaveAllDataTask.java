package com.example.hasmygamereleased.concurrency.task;

import com.example.hasmygamereleased.serialization.SerializationManager;
import javafx.concurrent.Task;

public class SaveAllDataTask extends Task<Boolean> {

    @Override
    protected Boolean call() {
        SerializationManager.INSTANCE.saveAll();
        return true;
    }
}
