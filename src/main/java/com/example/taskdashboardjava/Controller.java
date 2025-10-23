package com.example.taskdashboardjava;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class Controller {
    // Inject the Add Task button from your Main.fxml
    @FXML
    private Button AddTask;

    @FXML
    private AnchorPane contentArea;

    @FXML
    public void initialize() throws IOException {
        loadUI("AllTasks");
        System.out.println("Initialized with All Tasks view.");
    }

    @FXML
    private void showAllTasks() throws IOException {
        loadUI("AllTasks");
        System.out.println("All Tasks button clicked!");
    }

    @FXML
    private void handleAddTask() throws IOException {
        try {
            System.out.println("Add Task button clicked!");
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("AddTaskPopupWindows.fxml")));
            Parent popupRoot = loader.load();

//            create a new stage for the popup
            Stage popupStage = new Stage();

//            this make sure that the user can only interact with the popup window
            popupStage.initModality(Modality.APPLICATION_MODAL);

//            this make sure the popup window is in the center of the owner window
            Stage ownerStage = (Stage) AddTask.getScene().getWindow();
            popupStage.initOwner(ownerStage);

            Scene popupScene = new Scene(popupRoot);
            String cssFile = Objects.requireNonNull(this.getClass().getResource("/Application.css")).toExternalForm();
            popupScene.getStylesheets().add(cssFile);
            popupStage.setScene(popupScene);
            popupStage.resizableProperty().setValue(Boolean.FALSE);

//            waiting until the popup window in closed
            popupStage.showAndWait();


        }catch (Exception e){
            System.err.println("Error loading Add Task popup FXML:");
            e.printStackTrace();
        }
    }

    @FXML
    private void showWorkTasks() throws IOException {
        loadUI("WorkTasks");
        System.out.println("Work Tasks button clicked!");
    }

    @FXML
    private void showPersonalTasks() throws IOException {
        loadUI("PersonalTasks");
        System.out.println("Personal Tasks button clicked!");
    }

    @FXML
    private void showOtherTasks() throws IOException {
        loadUI("OthersTasks");
        System.out.println("Other Tasks button clicked!");
    }

    private void loadUI(String fxmlFileName) throws IOException {
        AnchorPane newPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFileName + ".fxml")));
        contentArea.getChildren().setAll(newPane);

        // Make it fit to the parent area
        AnchorPane.setTopAnchor(newPane, 0.0);
        AnchorPane.setBottomAnchor(newPane, 0.0);
        AnchorPane.setLeftAnchor(newPane, 0.0);
        AnchorPane.setRightAnchor(newPane, 0.0);

        // Optional: Add a fade animation for smooth transition
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), newPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
}