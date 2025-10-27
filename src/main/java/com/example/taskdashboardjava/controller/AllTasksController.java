package com.example.taskdashboardjava.controller;

import com.example.taskdashboardjava.database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets; // <-- 1. IMPORT ADDED
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane; // <-- 1. IMPORT ADDED
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AllTasksController {

    @FXML
    private VBox cardContainerVBox; // This is the main VBox from AllTasks.fxml

    @FXML
    public void initialize() {
        // This check prevents a crash if the fx:id is wrong in the FXML
        if (cardContainerVBox == null) {
            System.err.println("CRITICAL ERROR: cardContainerVBox is null. Check your fx:id in AllTasks.fxml.");
            return;
        }

        cardContainerVBox.getChildren().clear();

        Map<String, List<Task>> categorizedTasks = DatabaseHandler.getAllTasksByCategory();

        if (categorizedTasks == null || categorizedTasks.isEmpty()) {
            System.out.println("No tasks to display.");
            cardContainerVBox.getChildren().add(new Label("No tasks found. Add one!"));
            return;
        }

        // Loop through each category (e.g., "All Tasks", "Work")
        for (String category : categorizedTasks.keySet()) {

            // 1. Create and add the category title label
            Label categoryLabel = new Label(category);
            categoryLabel.getStyleClass().add("category-title");
            categoryLabel.setStyle("-fx-padding: 10 0 5 15; -fx-font-size: 20px; -fx-font-weight: bold;");
            cardContainerVBox.getChildren().add(categoryLabel);

            // 2. CREATE A NEW TILEPANE FOR THIS CATEGORY'S CARDS
            TilePane cardGrid = new TilePane();
            cardGrid.setPrefColumns(2); // This sets the 2-column layout
            cardGrid.setHgap(15);       // Horizontal spacing between cards
            cardGrid.setVgap(15);       // Vertical spacing between cards
            cardGrid.setPadding(new Insets(0, 15, 15, 15)); // This now works

            // 3. Loop through all tasks *in this category*
            for (Task task : categorizedTasks.get(category)) {
                try {
                    // 4. Load the Card.fxml for each task
                    // <-- 2. THIS PATH IS NOW CORRECTED (with /UI/)
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/taskdashboardjava/FXML/UI/Card.fxml")));

                    Parent taskCard = loader.load();

                    // 5. Get the card's controller and set its data
                    CardController cardController = loader.getController();
                    cardController.setData(task);

                    // 6. ADD THE CARD TO THE TILEPANE (not the VBox)
                    cardGrid.getChildren().add(taskCard);

                } catch (IOException e) {
                    System.err.println("Failed to load task card for: " + task.getTitle());
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    System.err.println("Failed to find Card.fxml. Check your path: /com/example/taskdashboardjava/FXML/UI/Card.fxml");
                    e.printStackTrace();
                }
            }

            // 7. ADD THE COMPLETED TILEPANE TO THE MAIN VBox
            cardContainerVBox.getChildren().add(cardGrid);
        }
    }
}