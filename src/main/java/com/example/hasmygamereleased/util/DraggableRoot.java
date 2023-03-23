package com.example.hasmygamereleased.util;

import javafx.scene.Parent;
import javafx.stage.Stage;

public class DraggableRoot {

    private double xoffset;
    private double yoffset;

    public void enableDraggableRoot(Stage stage, Parent root) {
        root.setOnMousePressed(event -> {
            xoffset = event.getSceneX();
            yoffset = event.getSceneY();
        });

        root.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - xoffset);
            stage.setY(e.getScreenY() - yoffset);
        });
    }
}
