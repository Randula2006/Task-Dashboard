package com.example.taskdashboardjava.controller;

import com.example.taskdashboardjava.database.DatabaseHandler;
import javafx.animation.FadeTransition;
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

    /**
     * Tracks the name of the currently loaded FXML file (e.g., "AllTasks")
     * This is crucial for reloading the view.
     */
    private String currentView;

    @FXML private AnchorPane categoryContainer;
    @FXML private Button AddTask;
    @FXML private AnchorPane contentArea;

    @FXML
    public void initialize() throws IOException {
        loadCategories(); // Load categories into the sidebar

        // This logic programmatically "clicks" the first category (e.g., "All") on startup
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
        this.currentView = "AllTasks";
        loadUI(currentView);
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

            // Get the controller for the popup window
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

            // This line pauses execution and WAITS for the popup to be closed
            popupStage.showAndWait();

            /**
             * This code runs ONLY after the popup is closed.
             * We check the flag from the popup's controller.
             */
            if (popupController.isTaskAdded()) {
                // If a task was added, reload the view that is currently active.
                System.out.println("Task added. Reloading view: " + this.currentView);
                loadUI(this.currentView);
            } else {
                System.out.println("Add task window was cancelled.");
            }

        }catch (Exception e){
            System.err.println("Error loading Add Task popup FXML:");
            e.printStackTrace();
        }
    }

    /**
     * Loads the specified FXML file into the contentArea and updates the currentView tracker.
     */
    private void loadUI(String fxmlFileName) throws IOException {
        // Update the tracker with the name of the view we are about to load
        this.currentView = fxmlFileName;

        AnchorPane newPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/taskdashboardjava/FXML/" + fxmlFileName + ".fxml")));
        String cssFile = Objects.requireNonNull(this.getClass().getResource("/com/example/taskdashboardjava/CSS/Application.css")).toExternalForm();
        newPane.getStylesheets().add(cssFile);

        // Replace the old content with the new pane
        contentArea.getChildren().setAll(newPane);

        // Make the new pane fit the content area
        AnchorPane.setTopAnchor(newPane, 0.0);
        AnchorPane.setBottomAnchor(newPane, 0.0);
        AnchorPane.setLeftAnchor(newPane, 0.0);
        AnchorPane.setRightAnchor(newPane, 0.0);

        // Add a simple fade-in transition
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), newPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    /**
     * Creates and adds the category labels to the sidebar.
     */
    private void loadCategories(){
        List<String> dafultCategories = Arrays.asList("All" , "Work" , "Personal" , "Business");

        VBox categoryLabelBox = new VBox(5);
        categoryLabelBox.setPrefWidth(categoryContainer.getPrefWidth());

        AnchorPane.setTopAnchor(categoryLabelBox, 0.0);
        AnchorPane.setBottomAnchor(categoryLabelBox, 0.0);
        AnchorPane.setLeftAnchor(categoryLabelBox, 0.0);
        AnchorPane.setRightAnchor(categoryLabelBox, 0.0);

        for(String categoryName : dafultCategories){
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

    /**
     * Handles the logic for when a category label is clicked.
     */
    private void handleCategorySelection(String categoryName){
        System.out.println("Filtering tasks for category: " + categoryName);

        // TODO: This is where you will add logic to load different views
        // For now, we'll just reload "AllTasks" regardless
        try {
            if ("All".equals(categoryName)) {
                loadUI("AllTasks");
            } else {
                // In the future, you might load a different FXML or
                // pass the categoryName to AllTasksController
                System.out.println("Loading for " + categoryName + " is not implemented, loading AllTasks.");
                loadUI("AllTasks");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Visually highlight the selected category
        highlightSelectedCategory(categoryName);
    }

    /**
     * Updates the CSS style of the selected category label.
     */
    private void highlightSelectedCategory(String selectedCategoryName){
        if(!categoryContainer.getChildren().isEmpty() && categoryContainer.getChildren().get(0) instanceof VBox){
            VBox categoryLabelBox = (VBox) categoryContainer.getChildren().get(0);

            for(javafx.scene.Node node : categoryLabelBox.getChildren()){
                if(node instanceof Label){
                    Label categoryLabel = (Label) node;
                    // Clear all previous inline styles
                    categoryLabel.setStyle("");
                    // Remove the "selected" class if it exists
                    categoryLabel.getStyleClass().remove("selected-category");

                    if(categoryLabel.getText().equals(selectedCategoryName)){
                        // Add the "selected" class (defined in your CSS)
                        categoryLabel.getStyleClass().add("selected-category");
                    }
                }
            }
        }
    }
}