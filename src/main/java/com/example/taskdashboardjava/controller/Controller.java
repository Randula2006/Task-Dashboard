package com.example.taskdashboardjava.controller;

import com.example.taskdashboardjava.database.DatabaseHandler;
import javafx.animation.FadeTransition;
import com.example.taskdashboardjava.controller.CurrentView; // Make sure this import is correct
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Arrays;

public class Controller {
    private List<Task> tasks = new ArrayList<>();
    private String currentView;

    @FXML private AnchorPane categoryContainer;
    @FXML private Button AddTask;
    @FXML private AnchorPane contentArea;

    // --- ADD THIS NEW PUBLIC METHOD ---
    /**
     * This method will be called by the CardController after an edit is saved.
     * It refreshes both the category sidebar and the main task list.
     */
    public void refreshUI() {
        try {
            System.out.println("Refreshing entire UI...");
            loadCategories(); // Reload the sidebar

            // Reload the task list based on the currently selected category
            loadUI("AllTasks");

            // Re-highlight the selected category
            highlightSelectedCategory(CurrentView.selectedCategory);
        } catch (IOException e) {
            System.err.println("Failed to refresh UI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() throws IOException {
        loadCategories(); // Load categories into the sidebar

        // This logic programmatically "clicks" the first category
        if (!categoryContainer.getChildren().isEmpty() && categoryContainer.getChildren().get(0) instanceof VBox) {
            VBox categoryLabelsVBox = (VBox) categoryContainer.getChildren().get(0);
            if (!categoryLabelsVBox.getChildren().isEmpty() && categoryLabelsVBox.getChildren().get(0) instanceof Label) {
                Label firstCategoryLabel = (Label) categoryLabelsVBox.getChildren().get(0);
                firstCategoryLabel.fireEvent(new javafx.scene.input.MouseEvent(
                        javafx.scene.input.MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, javafx.scene.input.MouseButton.PRIMARY, 1,
                        false, false, false, false, true, false, false, false, false, false, null));
            }
        }

        // Set the initial view name *before* loading it
        CurrentView.selectedCategory = "All";
        loadUI("AllTasks");
        System.out.println("Initialized with All Tasks view.");
    }

    @FXML
    private void showAllTasks() throws IOException {
        loadUI("AllTasks");
        System.out.println("All Tasks button clicked!");
    }

    @FXML
    private void handleAddTask() throws IOException {
        try {
            System.out.println("Add Task button clicked!");
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/taskdashboardjava/FXML/AddTaskPopupWindows.fxml")));
            Parent popupRoot = loader.load();
            AddTaskPopupWindowsController popupController = loader.getController();

            // Create a new stage for the popup
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            Stage ownerStage = (Stage) AddTask.getScene().getWindow();
            popupStage.initOwner(ownerStage);

            Scene popupScene = new Scene(popupRoot);
            String cssFile = Objects.requireNonNull(this.getClass().getResource("/com/example/taskdashboardjava/CSS/Application.css")).toExternalForm();
            popupScene.getStylesheets().add(cssFile);
            popupStage.setScene(popupScene);
            popupStage.resizableProperty().setValue(Boolean.FALSE);

            popupStage.showAndWait();

            if (popupController.isTaskAdded()) {
                System.out.println("Task added. Reloading view...");
                // --- THIS IS THE FIX ---
                loadCategories(); // <-- This reloads the sidebar
                loadUI(this.currentView); // This reloads the tasks
                // --- END FIX ---
            } else {
                System.out.println("Add task window was cancelled.");
            }

        }catch (Exception e){
            System.err.println("Error loading Add Task popup FXML:");
            e.printStackTrace();
        }
    }

    private void loadUI(String fxmlFileName) throws IOException {
        this.currentView = fxmlFileName;
        AnchorPane newPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/taskdashboardjava/FXML/" + fxmlFileName + ".fxml")));
        String cssFile = Objects.requireNonNull(this.getClass().getResource("/com/example/taskdashboardjava/CSS/Application.css")).toExternalForm();
        newPane.getStylesheets().add(cssFile);
        contentArea.getChildren().setAll(newPane);
        AnchorPane.setTopAnchor(newPane, 0.0);
        AnchorPane.setBottomAnchor(newPane, 0.0);
        AnchorPane.setLeftAnchor(newPane, 0.0);
        AnchorPane.setRightAnchor(newPane, 0.0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), newPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    private void loadCategories(){
        // 1. Get all unique categories from the tasks table
        List<String> categories = DatabaseHandler.getAllCategories();

        // 2. Manually add "All" to the beginning of the list as a filter
        if (!categories.contains("All")) {
            categories.add(0, "All");
        }

        VBox categoryLabelBox = new VBox(5);
        categoryLabelBox.setPrefWidth(categoryContainer.getPrefWidth());

        AnchorPane.setTopAnchor(categoryLabelBox, 0.0);
        AnchorPane.setBottomAnchor(categoryLabelBox, 0.0);
        AnchorPane.setLeftAnchor(categoryLabelBox, 0.0);
        AnchorPane.setRightAnchor(categoryLabelBox, 0.0);

        for(String categoryName : categories){
            Label categoryLabel = new Label(categoryName);
            categoryLabel.getStyleClass().add("category-item");
            categoryLabel.setPrefWidth(Double.MAX_VALUE);
            categoryLabel.setOnMouseClicked(event -> {
                handleCategorySelection(categoryName);
            });
            categoryLabelBox.getChildren().add(categoryLabel);
        }
        categoryContainer.getChildren().clear();
        categoryContainer.getChildren().add(categoryLabelBox);
    }

    private void handleCategorySelection(String categoryName){
        System.out.println("Filtering tasks for category: " + categoryName);
        CurrentView.selectedCategory = categoryName;

        try {
            loadUI("AllTasks");
        } catch (IOException e) {
            e.printStackTrace();
        }

        highlightSelectedCategory(categoryName);
    }

    private void highlightSelectedCategory(String selectedCategoryName){
        if(!categoryContainer.getChildren().isEmpty() && categoryContainer.getChildren().get(0) instanceof VBox){
            VBox categoryLabelBox = (VBox) categoryContainer.getChildren().get(0);

            for(javafx.scene.Node node : categoryLabelBox.getChildren()){
                if(node instanceof Label){
                    Label categoryLabel = (Label) node;
                    categoryLabel.setStyle("");
                    categoryLabel.getStyleClass().remove("selected-category");
                    if(categoryLabel.getText().equals(selectedCategoryName)){
                        categoryLabel.getStyleClass().add("selected-category");
                    }
                }
            }
        }
    }
}