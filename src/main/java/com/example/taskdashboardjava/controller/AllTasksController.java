package com.example.taskdashboardjava.controller;

import com.example.taskdashboardjava.database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AllTasksController {

    @FXML
    private VBox cardContainerVBox; // This is the VBox from AllTasks.fxml

    @FXML
    public void initialize() {
        // Clear any existing content
        cardContainerVBox.getChildren().clear();

        // Get tasks grouped by category
        Map<String, List<Task>> categorizedTasks = DatabaseHandler.getAllTasksByCategory();

        if (categorizedTasks == null || categorizedTasks.isEmpty()) {
            System.out.println("No tasks to display.");
            cardContainerVBox.getChildren().add(new Label("No tasks found. Add one!"));
            return;
        }

        // Loop through each category
        for (String category : categorizedTasks.keySet()) {

            // 1. Create a label for the category title
            Label categoryLabel = new Label(category);
            categoryLabel.getStyleClass().add("category-title"); // Make sure to style this in your CSS

            // Add some space for the category label
            categoryLabel.setStyle("-fx-padding: 10 0 5 0; -fx-font-size: 16px; -fx-font-weight: bold;");

            // 2. Add the category label to the main VBox
            cardContainerVBox.getChildren().add(categoryLabel);

            // 3. Loop through all tasks in this category
            for (Task task : categorizedTasks.get(category)) {
                try {
                    // 4. Load the Card.fxml for each task
                    // !! Check this path is correct !!
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/taskdashboardjava/FXML/UI/Card.fxml")));

                    Parent taskCard = loader.load();

                    // 5. Get the card's controller and set its data
                    CardController cardController = loader.getController();
                    cardController.setData(task);

                    // 6. Add the loaded task card to the main VBox
                    cardContainerVBox.getChildren().add(taskCard);

                } catch (IOException e) {
                    System.err.println("Failed to load task card for: " + task.getTitle());
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    System.err.println("Failed to find Card.fxml. Check the path.");
                    e.printStackTrace();
                }
            }
        }
    }
}