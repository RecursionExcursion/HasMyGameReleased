package com.example.hasmygamereleased.controller;

import com.example.hasmygamereleased.concurrency.ThreadManager;
import com.example.hasmygamereleased.concurrency.task.AddAppToWatchListTask;
import com.example.hasmygamereleased.concurrency.task.RefreshGameListTask;
import com.example.hasmygamereleased.fx_nodes.SearchTableInitializer;
import com.example.hasmygamereleased.fx_nodes.WatchTableInitializer;
import com.example.hasmygamereleased.models.app.SteamApp;
import com.example.hasmygamereleased.repository.SteamDataInterface;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    public BorderPane mainPane;
    public TextField searchTextField;
    //Buttons
    public Button closeButton;
    public Button searchButton;
    public Button watchListButton;
    public Button addButton;
    public Button refreshDataButton;
    //TableViews
    public TableView<SteamApp> watchTable;
    public TableView<Map.Entry<Long, String>> searchTable;

    private boolean isDisplayingWatchTable;

    private final ThreadManager threadManager = ThreadManager.INSTANCE;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Create Exit button action
        closeButton.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> Platform.exit());

        searchTextField.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            //Disable windows default Undo
            if (e.getCode() == KeyCode.ENTER) {
                searchButtonClick();
                e.consume();
            }
        });
        new SteamDataInterface().getAppIdMap();
        displayWatchedAppsTable();
    }

    private void displayWatchedAppsTable() {
        new WatchTableInitializer(watchTable, threadManager).initializeTable();

        watchTable.setVisible(true);
        searchTable.setVisible(false);

        watchListButton.setVisible(false);
        addButton.setVisible(false);

        isDisplayingWatchTable = true;
    }

    public void displaySearchTable() {

        new SearchTableInitializer(searchTable, searchTextField.getText()).initializeTable();

        watchTable.setVisible(false);
        searchTable.setVisible(true);

        watchListButton.setVisible(true);
        addButton.setVisible(true);

        isDisplayingWatchTable = false;
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
        displayWatchedAppsTable();
    }

    public void searchButtonClick() {
        displaySearchTable();
    }

    public void refreshDataClick() {
        ThreadManager.INSTANCE.submit(new RefreshGameListTask(watchTable));
    }
}