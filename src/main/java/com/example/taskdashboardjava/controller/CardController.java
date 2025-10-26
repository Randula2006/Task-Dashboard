package com.example.taskdashboardjava.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CardController {
    @FXML private Label taskTitle;
    @FXML private Label taskStatus;
    @FXML private Label taskDueDate;
    @FXML private Label taskPriority;
    @FXML private Label taskDescription;
    @FXML private Button taskEditBtn;
    @FXML private Button taskDeleteBtn;

    public void setData(Task task){
        taskTitle.setText(task.getTitle());
        taskStatus.setText(task.getStatus());
        taskDueDate.setText(task.getDueDate());
        taskPriority.setText(task.getPriority());
        taskDescription.setText(task.getDescription());

    }
}
