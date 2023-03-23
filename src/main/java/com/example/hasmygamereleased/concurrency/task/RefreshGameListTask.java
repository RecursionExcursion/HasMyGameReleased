package com.example.hasmygamereleased.concurrency.task;

import com.example.hasmygamereleased.concurrency.ThreadManager;
import com.example.hasmygamereleased.models.app.SteamApp;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

public class RefreshGameListTask extends Task<Object> {

    private final TableView<SteamApp> watchTable;

    public RefreshGameListTask(TableView<SteamApp> watchTable) {
        this.watchTable = watchTable;
    }

    @Override
    protected Object call() throws Exception {
        Function<SteamApp, Future<SteamApp>> sendAppsToTheFuture = app ->
                (Future<SteamApp>) ThreadManager.INSTANCE.submit(new UpdateAppCallable(app));

        Function<Future<SteamApp>, SteamApp> getFuture = future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        };

        List<SteamApp> newApps = watchTable.getItems()
                                           .stream()
                                           .map(sendAppsToTheFuture)
                                           .map(getFuture)
                                           .toList();
        watchTable.setItems(FXCollections.observableArrayList(newApps));
        ThreadManager.INSTANCE.submit(new SaveWatchListTask(newApps));
        return null;
    }
}
