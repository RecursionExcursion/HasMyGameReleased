package com.example.hasmygamereleased.controller;

import com.example.hasmygamereleased.concurrency.ThreadManager;
import com.example.hasmygamereleased.concurrency.task.AddAppToWatchListTask;
import com.example.hasmygamereleased.concurrency.task.RefreshGameListTask;
import com.example.hasmygamereleased.fx_nodes.SearchTableInitializer;
import com.example.hasmygamereleased.fx_nodes.WatchTableInitializer;
import com.example.hasmygamereleased.models.app.SteamApp;
import com.example.hasmygamereleased.repository.SteamDataInterface;
import com.example.hasmygamereleased.util.ClockLock;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Supplier;

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

    private final ClockLock refreshButtonClockLock = new ClockLock(30);
    private final ThreadManager threadManager = ThreadManager.INSTANCE;

    private final Supplier<Stage> currentStage = () -> (Stage) mainPane.getScene().getWindow();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchTextField.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                searchButtonClick();
                e.consume();
            }
        });
        new SteamDataInterface().getAppIdMap();
        displayWatchedAppsTable();
        setGraphicOnRefreshButton();
    }

    private void setGraphicOnRefreshButton() {
        Path currentRelativePath = Paths.get("");
        String absPath = currentRelativePath.toAbsolutePath().toString();

        Image image = new Image(absPath + "/src/main/resources/images/arrow-refresh-reload-icon-29.png",
                                25, 25, false, true);
        ImageView imageView = new ImageView(image);
        refreshDataButton.setGraphic(imageView);
    }

    private void displayWatchedAppsTable() {
        new WatchTableInitializer(watchTable).initializeTable();

        watchTable.setVisible(true);
        searchTable.setVisible(false);

        watchListButton.setVisible(false);
        addButton.setVisible(false);
    }

    public void displaySearchTable() {

        new SearchTableInitializer(searchTable, searchTextField.getText()).initializeTable();

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
        displayWatchedAppsTable();
    }

    public void searchButtonClick() {
        displaySearchTable();
    }

    public void refreshDataClick() {
        if(!refreshButtonClockLock.isLocked(LocalDateTime.now())){
            ThreadManager.INSTANCE.submit(new RefreshGameListTask(watchTable));
            refreshButtonClockLock.lock(LocalDateTime.now());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Game data is already up to date");
            alert.show();
        }
    }

    public void closeApplicationClick( ) {
        currentStage.get().close();
    }
}