module com.example.application {

    requires javafx.controls;
    requires javafx.fxml;

    exports application;
    exports application.controllers;
    exports application.models;
    exports application.utils;

    opens application to javafx.graphics;
    opens application.controllers to javafx.fxml;

}