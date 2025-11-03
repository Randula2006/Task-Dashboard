package com.example.taskdashboardjava.controller;

import com.example.taskdashboardjava.database.DatabaseHandler;
import com.example.taskdashboardjava.model.TaskPriority;
import com.example.taskdashboardjava.model.TaskStatus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    // This field holds the task we are editing
    private Task currentTask;

    // This flag will tell the CardController to refresh
    private boolean taskSaved = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize Priority ComboBox
        editTaskPriority.getItems().setAll(TaskPriority.values());
        editTaskPriority.setPromptText("Select Priority");
        setupPriorityComboBox(); // Call the setup method

        // Initialize Status ComboBox
        editTaskStatus.getItems().setAll(TaskStatus.values());
        editTaskStatus.setPromptText("Select Status");
        setupStatusComboBox(); // Call the setup method

        editGetPriorityColor.setValue(Color.web("#1e5ae0"));
    }

    // This method receives the task data from the card
    public void setTask(Task task) {
        this.currentTask = task; // Store the task object

        // Pre-fill the form fields
        editTaskTitle.setText(task.getTitle());
        editTaskDescription.setText(task.getDescription());
        editTaskDueDate.setValue(task.getDueDate());
        editTaskPriority.setValue(task.getPriority());
        editTaskStatus.setValue(task.getStatus());

        if (task.getPriorityColor() != null && !task.getPriorityColor().isEmpty()) {
            try {
                editGetPriorityColor.setValue(Color.web(task.getPriorityColor()));
            } catch (IllegalArgumentException e) {
                editGetPriorityColor.setValue(Color.web("#1e5ae0")); // Fallback
            }
        }
    }

    /**
     * This is the "reload" part. The CardController will call this
     * after the popup closes to see if it needs to refresh.
     */
    public boolean isTaskSaved() {
        return taskSaved;
    }

    @FXML
    private void handleClose() {
        // Get the stage from any FXML element (like the title field) and close it
        Stage stage = (Stage) editTaskTitle.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        handleClose();
    }

    /**
     * This is the "Save" part.
     * It updates the database and sets the "taskSaved" flag.
     */
    @FXML
    private void handleSaveChanges() {
        // 1. Validation (you can add more here)
        if (editTaskTitle.getText() == null || editTaskTitle.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Task title cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        if (currentTask == null) {
            showAlert("Error", "No task loaded for editing. Cannot save.", Alert.AlertType.ERROR);
            return;
        }

        // 2. Update the local Task object with new values
        currentTask.setTitle(editTaskTitle.getText().trim());
        currentTask.setDescription(editTaskDescription.getText().trim());
        currentTask.setDueDate(editTaskDueDate.getValue());
        currentTask.setPriority(editTaskPriority.getValue());
        currentTask.setStatus(editTaskStatus.getValue());
        currentTask.setPriorityColor(toWebColor(editGetPriorityColor.getValue()));

        // 3. THIS IS THE DATABASE SAVE
        DatabaseHandler.updateTask(currentTask);

        // 4. THIS IS THE SIGNAL FOR THE RELOAD
        this.taskSaved = true;

        // 5. Close the popup
        handleClose();
    }

    // Helper method to show alerts
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to format Color object to a hex string
    private String toWebColor(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    // --- ComboBox helper methods ---
    private void setupPriorityComboBox() {
        // (Your existing setupPriorityComboBox method)
    }

    private void setupStatusComboBox() {
        // (Your existing setupStatusComboBox method)
    }
}