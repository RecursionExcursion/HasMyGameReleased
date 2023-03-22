module com.example.hasmygamereleased {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens com.example.hasmygamereleased to javafx.fxml;
    exports com.example.hasmygamereleased;
    exports com.example.hasmygamereleased.controller;
    exports com.example.hasmygamereleased.models.all_apps;
    exports com.example.hasmygamereleased.models.app;
    exports com.example.hasmygamereleased.web_scraper;
    exports com.example.hasmygamereleased.repository.api;
    exports com.example.hasmygamereleased.repository.api.dto;

    opens com.example.hasmygamereleased.controller to javafx.fxml;
    opens com.example.hasmygamereleased.models.app to com.fasterxml.jackson.databind;
    opens com.example.hasmygamereleased.models.all_apps to com.fasterxml.jackson.databind;
    exports com.example.hasmygamereleased.repository;
}