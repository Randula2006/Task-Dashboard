/**
 * Renders task cards for the currently selected category using a simple grid layout.
 * Lean and purposeful, like a method that actually returns.
 */
package com.example.taskdashboardjava.controller;

import com.example.taskdashboardjava.database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AllTasksController {

    @FXML
    private VBox cardContainerVBox;

    @FXML
    /**
     * Populates the container with cards filtered by {@link CurrentView#selectedCategory}.
     * Shows an empty state when no tasks are available.
     */
    public void initialize() {
        if (cardContainerVBox == null) {
            System.err.println("CRITICAL ERROR: cardContainerVBox is null. Check your fx:id in AllTasks.fxml.");
            return;
        }

        cardContainerVBox.getChildren().clear();
        String categoryToLoad = CurrentView.selectedCategory;
        System.out.println("AllTasksController loading tasks for: " + categoryToLoad);
        List<Task> tasks = DatabaseHandler.getTasksByCategory(categoryToLoad);

        if (tasks == null || tasks.isEmpty()) {
            System.out.println("No tasks to display for this category.");
            Label noTasksLabel = new Label("No tasks available for '" + categoryToLoad + "'");
            noTasksLabel.setStyle("-fx-font-size: 16px; -fx-padding: 20; -fx-text-fill: gray; -fx-alignment: center;");
            cardContainerVBox.getChildren().add(noTasksLabel);
            return;
        }
        Label categoryLabel = new Label(categoryToLoad);
        categoryLabel.getStyleClass().add("category-title");
        categoryLabel.setStyle("-fx-padding: 10 0 5 15; -fx-font-size: 20px; -fx-font-weight: bold;");
        cardContainerVBox.getChildren().add(categoryLabel);
        TilePane cardGrid = new TilePane();
        cardGrid.setPrefColumns(2);
        cardGrid.setHgap(15);
        cardGrid.setVgap(15);
        cardGrid.setPadding(new Insets(0, 15, 15, 15));

        for (Task task : tasks) {
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/taskdashboardjava/FXML/UI/Card.fxml")));
                Parent taskCard = loader.load();
                CardController cardController = loader.getController();
                cardController.setData(task);
                cardGrid.getChildren().add(taskCard);

            } catch (IOException e) {
                System.err.println("Failed to load task card for: " + task.getTitle());
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.err.println("Failed to find Card.fxml. Check your path: /com/example/taskdashboardjava/FXML/UI/Card.fxml");
                e.printStackTrace();
            }
        }
        cardContainerVBox.getChildren().add(cardGrid);
    }
}