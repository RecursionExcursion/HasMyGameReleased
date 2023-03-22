package com.example.hasmygamereleased.fx_nodes;

import com.example.hasmygamereleased.concurrency.ThreadManager;
import com.example.hasmygamereleased.concurrency.task.LoadGameListCallable;
import com.example.hasmygamereleased.concurrency.task.RemoveAppFromWatchListTask;
import com.example.hasmygamereleased.models.app.SteamApp;
import javafx.application.HostServices;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class WatchTable implements TableViewNode {

    private final TableView<SteamApp> table;
    private final ThreadManager threadManager;

    public WatchTable(TableView<SteamApp> table, ThreadManager threadManager) {
        this.table = table;
        this.threadManager = threadManager;

    }

    @Override
    public void initializeTable() {

        Future<?> gameListFuture = threadManager.submit(new LoadGameListCallable());

        //Column Set up
        TableColumn<SteamApp, String> column1 = new TableColumn<>("Title");
        column1.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getTitle()));
        column1.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getTitle()));
        column1.prefWidthProperty().bind(table.widthProperty().multiply(1.00 / 3));

        TableColumn<SteamApp, String> column2 = new TableColumn<>("Release Date");
        column2.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getReleaseDate()));
        column2.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getReleaseDate()));
        column2.prefWidthProperty().bind(table.widthProperty().multiply(1.00 / 3));

        TableColumn<SteamApp, SteamApp> buttonCol = new TableColumn<>();
        buttonCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()));
        buttonCol.setCellFactory(p -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            private final Hyperlink hyperlink = new Hyperlink("Steam Page");
            final HBox hBox = new HBox(hyperlink, deleteButton);

            @Override
            protected void updateItem(final SteamApp app, boolean empty) {
                super.updateItem(app, empty);

                if (app == null) {
                    setGraphic(null);
                    return;
                }

                hBox.setSpacing(5);
                setGraphic(hBox);

                hyperlink.setTooltip(new Tooltip(app.getUrl()));

                hyperlink.setOnAction(event -> {
                    Node node = (Node) event.getSource();
                    Stage thisStage = (Stage) node.getScene().getWindow();

                    HostServices hostServices = (HostServices) thisStage.getProperties().get("hostServices");
                    hostServices.showDocument(app.getUrl());
                });


                deleteButton.setOnAction(event -> {
                    threadManager.submit(new RemoveAppFromWatchListTask(app));
                    getTableView().getItems().remove(app);
                });
            }
        });
        buttonCol.prefWidthProperty().bind(table.widthProperty().multiply(1.00 / 3));

        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Populate table
        table.getColumns().setAll(column1, column2, buttonCol);

        List<SteamApp> steamApps;
        try {
            steamApps = (List<SteamApp>) gameListFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        table.getItems().setAll(steamApps);
    }
}
