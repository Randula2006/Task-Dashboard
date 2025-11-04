/**
 * Controller for a single task card component.
 * Binds task data to UI, and coordinates edit/delete flows.
 */
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
    @FXML private Label taskStatus;
    @FXML private Label taskDueDate;
    @FXML private Label taskPriority;
    @FXML private Button taskEditBtn;
    @FXML private Button taskDeleteBtn;
    @FXML private Pane rootPane;

    private Task currentTask;

    /**
     * Populates the UI labels and styles using the provided task data.
     * @param task the task to display
     */
    public void setData(Task task){
        this.currentTask = task;
        taskTitle.setText(task.getTitle());
        taskDueDate.setText(task.getDueDate() != null ? task.getDueDate().toString() : "No Date");
        taskPriority.setText(task.getPriority() != null ? task.getPriority().getName() : "N/A");
        taskStatus.setText(task.getDescription() != null ? task.getDescription() : "");

        String color = task.getPriorityColor();
        if (color == null || color.isEmpty() || !color.startsWith("#")) {
            color = "#1e5ae0";
        }
        rootPane.setStyle("-fx-border-color: " + color + ";");
    }

    @FXML
    /**
     * Opens the edit dialog, then refreshes the main UI if changes were saved.
     */
    private void handleEditTask() {
        if (currentTask == null) {
            System.err.println("No task data to edit.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/taskdashboardjava/FXML/EditPopupWindow.fxml")));
            Parent popupRoot = loader.load();
            editTaskPopupWindow popupController = loader.getController();
            popupController.setTask(currentTask);
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(taskEditBtn.getScene().getWindow());
            popupStage.setTitle("Edit Task");

            Scene popupScene = new Scene(popupRoot);
            String cssFile = Objects.requireNonNull(this.getClass().getResource("/com/example/taskdashboardjava/CSS/Application.css")).toExternalForm();
            popupScene.getStylesheets().add(cssFile);
            popupStage.setScene(popupScene);
            popupStage.setResizable(false);
            popupStage.showAndWait();
            if (popupController.isTaskSaved()) {
                System.out.println("Task saved. Refreshing main UI.");
                Controller mainController = (Controller) taskEditBtn.getScene().getRoot().getUserData();
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
    /**
     * Asks for confirmation, deletes the task, and refreshes the UI.
     * Because who hasn’t deleted the wrong thing at least once? (We confirm.)
     */
    private void handleDeleteTask() {
        if (currentTask == null) {
            System.err.println("No task data to delete.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Task");
        alert.setHeaderText("Are you sure you want to delete this task?");
        alert.setContentText("Task: " + currentTask.getTitle());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                DatabaseHandler.deleteTask(currentTask.getId());
                Controller mainController = (Controller) taskEditBtn.getScene().getRoot().getUserData();
                mainController.refreshUI();

            } catch (Exception e) {
                System.err.println("An unexpected error occurred in handleDeleteTask:");
                e.printStackTrace();
            }
        }
    }
}