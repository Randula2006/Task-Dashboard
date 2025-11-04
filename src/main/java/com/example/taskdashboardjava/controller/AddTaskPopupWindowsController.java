/**
 * Controller for the "Add Task" modal dialog.
 * Captures user input, persists to the database, and closes politely.
 */
package com.example.taskdashboardjava.controller;

import com.example.taskdashboardjava.database.DatabaseHandler;
import com.example.taskdashboardjava.model.TaskPriority;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class AddTaskPopupWindowsController implements Initializable {

    private boolean taskAdded = false;

    @FXML
    private TextField addTaskTitle;
    @FXML
    private TextArea addTaskDescription;
    @FXML
    private DatePicker addTaskDate;
    @FXML
    private ComboBox<TaskPriority> addTaskPriority;
    @FXML
    private Button createAddTask;
    @FXML
    private Button cancelAddTask;

    /**
     * Handles the Create/Add action: validates, persists, and closes the dialog.
     * @param event UI action event (because buttons love company)
     */
    public void submitAddTask(ActionEvent event) {
        String title = addTaskTitle.getText();
        String description = addTaskDescription.getText();
        String date = (addTaskDate.getValue() != null) ? addTaskDate.getValue().toString() : "";
        TaskPriority priority = addTaskPriority.getSelectionModel().getSelectedItem();

        System.out.println("Task Added:");
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Date: " + date);
        System.out.println("Priority: " + (priority != null ? priority.getName() : "None"));

        DatabaseHandler.InsertTask(title, description, date, (priority != null ? priority.getName() : "None"));
        this.taskAdded = true;
        Stage stage = (Stage) createAddTask.getScene().getWindow();
        stage.close();
    }

    /**
     * Closes the dialog without saving anything.
     * @param event UI action event
     */
    public void cancelAddTask(ActionEvent event) {
        Stage stage = (Stage) cancelAddTask.getScene().getWindow();
        stage.close();
    }

    @Override
    /**
     * Initializes the dialog controls and cell renderers.
     */
    public void initialize(URL location, ResourceBundle resources) {
        addTaskPriority.getItems().setAll(TaskPriority.values());
        addTaskPriority.setCellFactory(lv -> new ListCell<TaskPriority>() {
            @Override
            protected void updateItem(TaskPriority priority, boolean empty) {
                super.updateItem(priority, empty);
                if (empty || priority == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(priority.getName());
                    setGraphic(priority.createGraphic());
                }
            }
        });
        addTaskPriority.setButtonCell(new ListCell<TaskPriority>() {
            @Override
            protected void updateItem(TaskPriority priority, boolean empty) {
                super.updateItem(priority, empty);
                if (empty || priority == null) {
                    setText(addTaskPriority.getPromptText());
                    setGraphic(null);
                } else {
                    setText(priority.getName());
                    setGraphic(priority.createGraphic());
                }
            }
        });
    }

    public boolean isTaskAdded() {
        /**
         * Indicates whether a task was successfully added.
         * @return true if a new task was persisted; false otherwise
         */
        return taskAdded;
    }
}
