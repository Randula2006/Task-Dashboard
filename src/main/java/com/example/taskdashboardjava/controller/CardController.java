package com.example.taskdashboardjava.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CardController {
    @FXML private Label taskTitle;
    @FXML private Label taskStatus; // This is the label we are re-purposing
    @FXML private Label taskDueDate;
    @FXML private Label taskPriority;
    @FXML private Button taskEditBtn;
    @FXML private Button taskDeleteBtn;

    private Task currentTask;

    public void setData(Task task){
        this.currentTask = task;

        taskTitle.setText(task.getTitle());
        taskDueDate.setText(task.getDueDate() != null ? task.getDueDate().toString() : "No Date");
        taskPriority.setText(task.getPriority() != null ? task.getPriority().getName() : "N/A");

        // Set the description to the taskStatus label
        taskStatus.setText(task.getDescription() != null ? task.getDescription() : "");
    }

    @FXML
    private void handleEditTask() {
        if (currentTask == null) {
            System.err.println("No task data to edit.");
            return;
        }

        try {
            // --- THIS IS THE FIX ---
            // The filename was "editTask..." but your file is "EditTask..."
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/taskdashboardjava/FXML/EditPopupWindow.fxml")));
            Parent popupRoot = loader.load();

            // 2. Get the popup's controller
            editTaskPopupWindow popupController = loader.getController();

            // 3. Pass the current task data to the popup
            popupController.setTask(currentTask);

            // 4. Create and configure the new stage (window)
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(taskEditBtn.getScene().getWindow());

            Scene popupScene = new Scene(popupRoot);
            String cssFile = Objects.requireNonNull(this.getClass().getResource("/com/example/taskdashboardjava/CSS/Application.css")).toExternalForm();
            popupScene.getStylesheets().add(cssFile);
            popupStage.setScene(popupScene);
            popupStage.resizableProperty().setValue(Boolean.FALSE);
            popupStage.setTitle("Edit Task");

            // 5. Show the popup and wait for it to be closed
            popupStage.showAndWait();

            // 6. Check if the task was saved
            if (popupController.isTaskSaved()) {
                // If saved, refresh this card's data with the updated task object
                System.out.println("Refreshing card data for: " + currentTask.getTitle());
                this.setData(this.currentTask);
            }

        } catch (IOException e) {
            System.err.println("Failed to load edit task popup:");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("CRITICAL: Failed to find FXML file. Check the path in CardController.java.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteTask() {
        System.out.println("Delete button clicked for task: " + currentTask.getTitle());
        // Here you would:
        // 1. Show a confirmation dialog
        // 2. Call DatabaseHandler.deleteTask(currentTask.getId())
        // 3. Find a way to refresh the main view (this is more complex)
    }
}