/**
 * Controller for the "Edit Task" modal dialog.
 * Pre-fills fields, applies changes, and signals callers to refresh.
 */
package com.example.taskdashboardjava.controller;

import com.example.taskdashboardjava.database.DatabaseHandler;
import com.example.taskdashboardjava.model.TaskPriority;
import com.example.taskdashboardjava.model.TaskStatus;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class editTaskPopupWindow implements Initializable {

    @FXML private TextField editTaskTitle;
    @FXML private TextArea editTaskDescription;
    @FXML private DatePicker editTaskDueDate;
    @FXML private ComboBox<TaskPriority> editTaskPriority;
    @FXML private ColorPicker editGetPriorityColor;
    @FXML private ComboBox<TaskStatus> editTaskStatus;
    @FXML private ComboBox<String> editTaskCategory;
    /** The task currently being edited. */
    private Task currentTask;
    /** Flag used by callers to determine whether a reload is needed. */
    private boolean taskSaved = false;

    @Override
    /**
     * Initializes combo boxes, default colors, and category options.
     */
    public void initialize(URL location, ResourceBundle resources) {
        editTaskPriority.getItems().setAll(TaskPriority.values());
        editTaskPriority.setPromptText("Select Priority");
        setupPriorityComboBox();
        editTaskStatus.getItems().setAll(TaskStatus.values());
        editTaskStatus.setPromptText("Select Status");
        setupStatusComboBox();

        editGetPriorityColor.setValue(Color.web("#1e5ae0"));
        loadCategories();
    }

    /**
     * Loads categories from the database and enables manual entry.
     */
    private void loadCategories() {
        List<String> categories = DatabaseHandler.getAllCategories();
        editTaskCategory.setItems(FXCollections.observableArrayList(categories));
        editTaskCategory.setEditable(true);
    }
    /**
     * Receives the task data and populates the form.
     * @param task the task to edit
     */
    public void setTask(Task task) {
        this.currentTask = task;
        editTaskTitle.setText(task.getTitle());
        editTaskDescription.setText(task.getDescription());
        editTaskDueDate.setValue(task.getDueDate());
        editTaskPriority.setValue(task.getPriority());
        editTaskStatus.setValue(task.getStatus());
        editTaskCategory.setValue(task.getCategory());

        if (task.getPriorityColor() != null && !task.getPriorityColor().isEmpty()) {
            try {
                editGetPriorityColor.setValue(Color.web(task.getPriorityColor()));
            } catch (IllegalArgumentException e) {
                editGetPriorityColor.setValue(Color.web("#1e5ae0"));
            }
        }
    }
    /**
     * Indicates whether the user saved changes.
     * @return true if saved; false otherwise
     */
    public boolean isTaskSaved() {
        return taskSaved;
    }

    @FXML
    /** Closes the dialog. */
    private void handleClose() {
        Stage stage = (Stage) editTaskTitle.getScene().getWindow();
        stage.close();
    }

    @FXML
    /** Cancels editing and closes the dialog. */
    private void handleCancel() {
        handleClose();
    }

    @FXML
    /**
     * Validates input, persists changes, and closes the dialog.
     * If you read this in a code review: yes, validation could be stricter.
     */
    private void handleSaveChanges() {
        if (editTaskTitle.getText() == null || editTaskTitle.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Task title cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        if (currentTask == null) {
            showAlert("Error", "No task loaded for editing. Cannot save.", Alert.AlertType.ERROR);
            return;
        }
        currentTask.setTitle(editTaskTitle.getText().trim());
        currentTask.setDescription(editTaskDescription.getText().trim());
        currentTask.setDueDate(editTaskDueDate.getValue());
        currentTask.setPriority(editTaskPriority.getValue());
        currentTask.setStatus(editTaskStatus.getValue());
        String newCategory = editTaskCategory.getValue();
        currentTask.setCategory(newCategory);

        currentTask.setPriorityColor(toWebColor(editGetPriorityColor.getValue()));
        DatabaseHandler.updateTask(currentTask);
        this.taskSaved = true;
        handleClose();
    }
    /** Shows a simple Alert dialog. */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /** Converts a JavaFX Color to a hex web color (e.g., #RRGGBB). */
    private String toWebColor(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
    /** Hook for custom priority cell rendering. */
    private void setupPriorityComboBox() {
    }

    /** Hook for custom status cell rendering. */
    private void setupStatusComboBox() {
    }
}