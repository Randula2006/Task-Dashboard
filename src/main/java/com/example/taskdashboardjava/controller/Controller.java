/**
 * Primary controller for the dashboard shell.
 * Manages:
 * - Sidebar categories
 * - Main content area swapping
 * - Add Task popup orchestration
 */
package com.example.taskdashboardjava.controller;

import com.example.taskdashboardjava.database.DatabaseHandler;
import javafx.animation.FadeTransition;
import com.example.taskdashboardjava.controller.CurrentView;
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
    /** In-memory tasks cache (lightweight; DB is source of truth). */
    private List<Task> tasks = new ArrayList<>();
    /** Tracks the current FXML view name loaded into the content area. */
    private String currentView;

    @FXML private AnchorPane categoryContainer;
    @FXML private Button AddTask;
    @FXML private AnchorPane contentArea;

    /**
     * Refreshes categories and reloads the main task list.
     * Called by child controllers after data changes.
     */
    public void refreshUI() {
        try {
            System.out.println("Refreshing entire UI...");
            loadCategories();
            loadUI("AllTasks");
            highlightSelectedCategory(CurrentView.selectedCategory);
        } catch (IOException e) {
            System.err.println("Failed to refresh UI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * Initializes the controller after FXML is loaded.
     * Preselects the first category and loads the default content.
     */
    public void initialize() throws IOException {
        loadCategories();
        if (!categoryContainer.getChildren().isEmpty() && categoryContainer.getChildren().get(0) instanceof VBox) {
            VBox categoryLabelsVBox = (VBox) categoryContainer.getChildren().get(0);
            if (!categoryLabelsVBox.getChildren().isEmpty() && categoryLabelsVBox.getChildren().get(0) instanceof Label) {
                Label firstCategoryLabel = (Label) categoryLabelsVBox.getChildren().get(0);
                firstCategoryLabel.fireEvent(new javafx.scene.input.MouseEvent(
                        javafx.scene.input.MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, javafx.scene.input.MouseButton.PRIMARY, 1,
                        false, false, false, false, true, false, false, false, false, false, null));
            }
        }
        CurrentView.selectedCategory = "All";
        loadUI("AllTasks");
        System.out.println("Initialized with All Tasks view.");
    }

    @FXML
    /** Loads the AllTasks view into the main content area. */
    private void showAllTasks() throws IOException {
        loadUI("AllTasks");
        System.out.println("All Tasks button clicked!");
    }

    @FXML
    /** Opens the Add Task popup and reloads UI on success. */
    private void handleAddTask() throws IOException {
        try {
            System.out.println("Add Task button clicked!");
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/taskdashboardjava/FXML/AddTaskPopupWindows.fxml")));
            Parent popupRoot = loader.load();
            AddTaskPopupWindowsController popupController = loader.getController();
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
                loadCategories();
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
     * Loads and displays an FXML file by name into the content area.
     * @param fxmlFileName name without extension, e.g., "AllTasks"
     */
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

    /**
     * Rebuilds the category sidebar by querying distinct categories from the DB.
     * Adds a synthetic "All" filter to the top, because we all need a reset button.
     */
    private void loadCategories(){
        List<String> categories = DatabaseHandler.getAllCategories();
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

    /**
     * Updates the selected category and reloads the AllTasks view.
     * @param categoryName the chosen category label text
     */
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

    /**
     * Applies visual highlighting to the selected category label.
     * @param selectedCategoryName category to accent
     */
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