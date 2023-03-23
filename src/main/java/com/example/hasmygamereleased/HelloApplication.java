package com.example.hasmygamereleased;

import com.example.hasmygamereleased.concurrency.task.SaveAllDataRunnable;
import com.example.hasmygamereleased.scene.CssManager;
import com.example.hasmygamereleased.util.DraggableRoot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("hello-view.fxml")));

        new DraggableRoot().enableDraggableRoot(stage, root);

        Scene scene = new Scene(root, 700, 500);
        //For rounded corner
        scene.setFill(Color.TRANSPARENT);
        //Optional Css Styling
        scene.getStylesheets().add(CssManager.INSTANCE.getCssUrl());

        stage.getProperties().put("hostServices", this.getHostServices());

        //For rounded corners
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(new SaveAllDataRunnable()));
        launch();
    }
}