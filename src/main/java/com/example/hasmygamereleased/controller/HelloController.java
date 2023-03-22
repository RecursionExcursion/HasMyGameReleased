package com.example.hasmygamereleased.controller;

import com.example.hasmygamereleased.FxNodes.SearchTable;
import com.example.hasmygamereleased.FxNodes.WatchTable;
import com.example.hasmygamereleased.concurrency.ThreadManager;
import com.example.hasmygamereleased.concurrency.task.AddAppToWatchListTask;
import com.example.hasmygamereleased.models.app.SteamApp;
import com.example.hasmygamereleased.repository.SteamDataInterface;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    public BorderPane mainPane;
    public Button closeButton;
    public Button searchButton;
    public TextField searchTextField;
    public Button watchListButton;
    public Button addButton;

    private final ThreadManager threadManager = ThreadManager.INSTANCE;

    public TableView<SteamApp> watchTable;
    public TableView<Map.Entry<Long, String>> searchTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Create Exit button action
        closeButton.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> Platform.exit());

        new SteamDataInterface().getAppIdMap();

        generateWatchedAppsTable();
    }

    private void generateWatchedAppsTable() {

        new WatchTable(watchTable, threadManager).initializeTable();

        watchTable.setVisible(true);
        searchTable.setVisible(false);
        watchListButton.setVisible(false);
        addButton.setVisible(false);
    }

    public void generateSearchTable() {

        new SearchTable(searchTable, searchTextField.getText()).initializeTable();

        watchTable.setVisible(false);
        searchTable.setVisible(true);
        watchListButton.setVisible(true);
        addButton.setVisible(true);
    }

    public void addToWatchListClick() {
        TableView.TableViewSelectionModel<Map.Entry<Long, String>> selectionModel = searchTable.getSelectionModel();
        if (!selectionModel.isEmpty()) {

            threadManager.submit(
                    new AddAppToWatchListTask(selectionModel.getSelectedItem(), watchTable.getItems())
            );
        }
    }

    public void backToWatchListClick() {
        generateWatchedAppsTable();
    }

    public void searchButtonClick() {
        generateSearchTable();
    }
}