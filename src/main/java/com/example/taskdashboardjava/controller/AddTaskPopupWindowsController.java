package com.example.taskdashboardjava.controller;

import com.example.taskdashboardjava.model.TaskPriority;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class AddTaskPopupWindowsController implements Initializable {

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

        Stage stage = (Stage) createAddTask.getScene().getWindow();
        stage.close();
    }

    public void cancelAddTask(ActionEvent event) {
        Stage stage = (Stage) cancelAddTask.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1. Populate the ComboBox with the enum values
        addTaskPriority.getItems().setAll(TaskPriority.values());

        // 2. Set the custom cell factory to display text and graphics (icons)
        // This affects how items are displayed in the dropdown list
        addTaskPriority.setCellFactory(lv -> new ListCell<TaskPriority>() {
            @Override
            protected void updateItem(TaskPriority priority, boolean empty) {
                super.updateItem(priority, empty);
                if (empty || priority == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(priority.getName()); // Display the name (e.g., "High")
                    setGraphic(priority.createGraphic()); // Display the colored circle
                }
            }
        });

        // 3. Set the button cell factory
        // This affects how the *selected item* is displayed when the ComboBox is closed
        addTaskPriority.setButtonCell(new ListCell<TaskPriority>() {
            @Override
            protected void updateItem(TaskPriority priority, boolean empty) {
                super.updateItem(priority, empty);
                if (empty || priority == null) {
                    setText(addTaskPriority.getPromptText()); // Display prompt if nothing selected
                    setGraphic(null);
                } else {
                    setText(priority.getName());
                    setGraphic(priority.createGraphic());
                }
            }
        });

        // Optional: Set a default selection if you don't want the prompt to persist until selection
        // addTaskPriority.getSelectionModel().select(TaskPriority.MEDIUM);

        // Add event handler for the "Add" button
//        createAddTask.setOnAction(event -> handleAddTask());
        // For the cancel button, you'd add:
        // cancelButton.setOnAction(event -> handleCancel());
    }


}
