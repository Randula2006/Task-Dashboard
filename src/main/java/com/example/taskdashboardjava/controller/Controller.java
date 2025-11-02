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
    private String currentView;

    // Inject the Add Task button from your Main.fxml
    @FXML private AnchorPane categoryContainer;
    @FXML private Button AddTask;
    @FXML private AnchorPane contentArea;

    @FXML
    public void initialize() throws IOException {
        loadCategories(); // Load categories into the sidebar
        if (!categoryContainer.getChildren().isEmpty() && categoryContainer.getChildren().get(0) instanceof VBox) {
            VBox categoryLabelsVBox = (VBox) categoryContainer.getChildren().get(0);
            if (!categoryLabelsVBox.getChildren().isEmpty() && categoryLabelsVBox.getChildren().get(0) instanceof Label) {
                Label firstCategoryLabel = (Label) categoryLabelsVBox.getChildren().get(0);
                // Simulate a mouse click event on the first label ("All")
                // This triggers handleCategorySelection and highlightSelectedCategory for "All"
                firstCategoryLabel.fireEvent(new javafx.scene.input.MouseEvent(
                        javafx.scene.input.MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, javafx.scene.input.MouseButton.PRIMARY, 1,
                        false, false, false, false, true, false, false, false, false, false, null));
            }
        }

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

            AddTaskPopupWindowsController popupController = loader.getController();

//            create a new stage for the popup
            Stage popupStage = new Stage();

//            this make sure that the user can only interact with the popup window
            popupStage.initModality(Modality.APPLICATION_MODAL);

//            this make sure the popup window is in the center of the owner window
            Stage ownerStage = (Stage) AddTask.getScene().getWindow();
            popupStage.initOwner(ownerStage);

            Scene popupScene = new Scene(popupRoot);
            String cssFile = Objects.requireNonNull(this.getClass().getResource("/com/example/taskdashboardjava/CSS/Application.css")).toExternalForm();
            popupScene.getStylesheets().add(cssFile);
            popupStage.setScene(popupScene);
            popupStage.resizableProperty().setValue(Boolean.FALSE);

//            waiting until the popup window in closed
            popupStage.showAndWait();

            if (popupController.isTaskAdded()) {
                // If a task was added, reload the current view
                loadUI(this.currentView);
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

        // Make it fit to the parent area
        AnchorPane.setTopAnchor(newPane, 0.0);
        AnchorPane.setBottomAnchor(newPane, 0.0);
        AnchorPane.setLeftAnchor(newPane, 0.0);
        AnchorPane.setRightAnchor(newPane, 0.0);

        // Optional: Add a fade animation for smooth transition
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), newPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

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
                // Here you can add logic to filter tasks based on the selected category
                handleCategorySelection(categoryName);
            });

            categoryLabelBox.getChildren().add(categoryLabel);
        }

        categoryContainer.getChildren().clear();
        categoryContainer.getChildren().add(categoryLabelBox);
    }

    private void handleCategorySelection(String categoryName){
        System.out.println("Filtering tasks for category: " + categoryName);
        // TODO: In the future, this is where you'll load tasks from the database
        //       that belong to this 'categoryName' and update the main display
        //       (which is currently 'contentArea' in your FXML).
        // Visually highlight the selected category
        highlightSelectedCategory(categoryName);
    }

    private void highlightSelectedCategory(String selectedCategoryName){

        if(!categoryContainer.getChildren().isEmpty() && categoryContainer.getChildren().get(0) instanceof VBox){
            VBox categoryLabelBox = (VBox) categoryContainer.getChildren().get(0);

            for(javafx.scene.Node node : categoryLabelBox.getChildren()){
                if(node instanceof Label){
                    Label categoryLabel = (Label) node;
                    if(categoryLabel.getText().equals(selectedCategoryName)){
                        categoryLabel.setStyle("-fx-background-color: #d0e6ff; -fx-font-weight: bold;");
                    } else {
                        categoryLabel.setStyle(""); // Reset style for non-selected categories
                    }
                }
            }
        }
    }
}