package com.example.taskdashboardjava;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
//        loadUI("AllTasks");
    }

    @FXML
    private void showAllTasks() throws IOException {
//        loadUI("AllTasks");
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

//            waiting until the popup window in closed
            popupStage.showAndWait();


        }catch (Exception e){
            System.err.println("Error loading Add Task popup FXML:");
            e.printStackTrace();
        }
    }

    @FXML
    private void showWorkTasks() throws IOException {
//        loadUI("WorkTasks");
    }

    @FXML
    private void showPersonalTasks() throws IOException {
//        loadUI("PersonalTasks");
    }

    @FXML
    private void showOtherTasks() throws IOException {
//        loadUI("OthersTasks");
    }

    private void loadUI(String fxml) throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml + ".fxml")));
        contentArea.getChildren().setAll(pane);
    }

}