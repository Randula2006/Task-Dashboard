package com.example.taskdashboardjava;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class Controller {

    @FXML
    private AnchorPane contentArea;

    @FXML
    public void initialize() throws IOException {
        loadUI("AllTasks");
    }

    @FXML
    private void showAllTasks() throws IOException {
        loadUI("AllTasks");
    }

    @FXML
    private void showWorkTasks() throws IOException {
        loadUI("WorkTasks");
    }

    @FXML
    private void showPersonalTasks() throws IOException {
        loadUI("PersonalTasks");
    }

    @FXML
    private void showOtherTasks() throws IOException {
        loadUI("OthersTasks");
    }

    private void loadUI(String fxml) throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml + ".fxml")));
        contentArea.getChildren().setAll(pane);
    }

}