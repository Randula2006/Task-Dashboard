package com.example.taskdashboardjava.controller;

import com.example.taskdashboardjava.database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class CardController {
    @FXML private Label taskTitle;
    @FXML private Label taskStatus; // This label now shows the description
    @FXML private Label taskDueDate;
    @FXML private Label taskPriority;
    @FXML private Button taskEditBtn;
    @FXML private Button taskDeleteBtn;
    @FXML private Pane rootPane;

    private Task currentTask;

    // This method populates the card
    public void setData(Task task){
        this.currentTask = task;
        taskTitle.setText(task.getTitle());
        taskDueDate.setText(task.getDueDate() != null ? task.getDueDate().toString() : "No Date");
        taskPriority.setText(task.getPriority() != null ? task.getPriority().getName() : "N/A");
        taskStatus.setText(task.getDescription() != null ? task.getDescription() : ""); // Show description

        String color = task.getPriorityColor();

        // Set a default if the color is null or invalid
        if (color == null || color.isEmpty() || !color.startsWith("#")) {
            color = "#1e5ae0"; // Default blue
        }

        // Apply the color directly to the pane's border
        rootPane.setStyle("-fx-border-color: " + color + ";");
    }

    /**
     * This method handles both opening the popup AND reloading the card.
     */
    @FXML
    private void handleEditTask() {
        if (currentTask == null) {
            System.err.println("No task data to edit.");
            return;
        }

        try {
            // 1. Load the popup FXML (fixed the capitalization error)
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/taskdashboardjava/FXML/EditPopupWindow.fxml")));
            Parent popupRoot = loader.load();

            // 2. Get the popup's controller
            editTaskPopupWindow popupController = loader.getController();

            // 3. Pass the current task data to the popup
            popupController.setTask(currentTask);

            // 4. Create and configure the new stage
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(taskEditBtn.getScene().getWindow());
            popupStage.setTitle("Edit Task");

            Scene popupScene = new Scene(popupRoot);
            String cssFile = Objects.requireNonNull(this.getClass().getResource("/com/example/taskdashboardjava/CSS/Application.css")).toExternalForm();
            popupScene.getStylesheets().add(cssFile);
            popupStage.setScene(popupScene);
            popupStage.setResizable(false);

            // 5. Show the popup and WAIT for it to be closed
            popupStage.showAndWait();

            // 6. --- THIS IS THE "RELOAD" PART (MODIFIED) ---
            // After the popup is closed, check the flag from its controller
            if (popupController.isTaskSaved()) {
                System.out.println("Task saved. Refreshing main UI.");

                // 1. Get the main controller we stored in Dashboard.java
                Controller mainController = (Controller) taskEditBtn.getScene().getRoot().getUserData();

                // 2. Call the new refresh method
                mainController.refreshUI();
            }

        } catch (IOException e) {
            System.err.println("Failed to load edit task popup:");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("CRITICAL: Failed to find FXML file or Main Controller. Check path in CardController and UserData in Dashboard.java.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred in handleEditTask:");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteTask() {
        if (currentTask == null) {
            System.err.println("No task data to delete.");
            return;
        }

        // 1. Create a confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Task");
        alert.setHeaderText("Are you sure you want to delete this task?");
        alert.setContentText("Task: " + currentTask.getTitle());

        // 2. Show the alert and wait for a response
        Optional<ButtonType> result = alert.showAndWait();

        // 3. Check if the user clicked "OK"
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // 4. Delete from database
                DatabaseHandler.deleteTask(currentTask.getId());

                // 5. Refresh the entire UI
                Controller mainController = (Controller) taskEditBtn.getScene().getRoot().getUserData();
                mainController.refreshUI();

            } catch (Exception e) {
                System.err.println("An unexpected error occurred in handleDeleteTask:");
                e.printStackTrace();
            }
        }
    }
}