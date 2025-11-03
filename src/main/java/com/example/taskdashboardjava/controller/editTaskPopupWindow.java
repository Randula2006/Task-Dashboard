package com.example.taskdashboardjava.controller;

import com.example.taskdashboardjava.database.DatabaseHandler;
import com.example.taskdashboardjava.model.TaskPriority;
import com.example.taskdashboardjava.model.TaskStatus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class editTaskPopupWindow implements Initializable {

    @FXML private TextField editTaskTitle;
    @FXML private TextArea editTaskDescription;
    @FXML private DatePicker editTaskDueDate;
    @FXML private ComboBox<TaskPriority> editTaskPriority;
    @FXML private ColorPicker editGetPriorityColor;
    @FXML private ComboBox<TaskStatus> editTaskStatus;
    @FXML private Pane rootPane;

    private Task currentTask;
    private boolean taskSaved = false; // Flag to indicate if changes were saved

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editTaskPriority.getItems().setAll(TaskPriority.values());
        setupPriorityComboBox();

        editTaskStatus.getItems().setAll(TaskStatus.values());
        setupStatusComboBox();

        editGetPriorityColor.setValue(Color.web("#1e5ae0"));
    }

    public void setTask(Task task) {
        this.currentTask = task;

        editTaskTitle.setText(task.getTitle());
        editTaskDescription.setText(task.getDescription());
        editTaskDueDate.setValue(task.getDueDate());
        editTaskPriority.setValue(task.getPriority());
        editTaskStatus.setValue(task.getStatus());

        if (task.getPriorityColor() != null && !task.getPriorityColor().isEmpty()) {
            try {
                editGetPriorityColor.setValue(Color.web(task.getPriorityColor()));
            } catch (Exception e) {
                editGetPriorityColor.setValue(Color.web("#1e5ae0")); // Fallback
            }
        }
    }

    // Getter for the save flag
    public boolean isTaskSaved() {
        return taskSaved;
    }

    @FXML
    private void handleSaveChanges() {
        // ... (Your validation code is good, keep it here) ...
        if (editTaskTitle.getText() == null || editTaskTitle.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Task title cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        if (currentTask == null) {
            showAlert("Error", "No task loaded for editing.", Alert.AlertType.ERROR);
            return;
        }

        // Update the Task object
        currentTask.setTitle(editTaskTitle.getText().trim());
        currentTask.setDescription(editTaskDescription.getText().trim());
        currentTask.setDueDate(editTaskDueDate.getValue());
        currentTask.setPriority(editTaskPriority.getValue());
        currentTask.setStatus(editTaskStatus.getValue());
        currentTask.setPriorityColor(toWebColor(editGetPriorityColor.getValue()));

        // --- FIXED: Call the database handler to save the changes ---
        DatabaseHandler.updateTask(currentTask);
        this.taskSaved = true; // Set flag to true

        handleClose();
    }

    @FXML
    private void handleCancel() {
        handleClose();
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    // Helper method to format Color object to a hex string
    private String toWebColor(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    // Helper method to show alerts
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // --- (Your ComboBox setup helper methods are good, keep them here) ---
    private void setupPriorityComboBox() {
        // ...
    }
    private void setupStatusComboBox() {
        // ...
    }
}