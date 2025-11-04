/**
 * JavaFX application bootstrap for the Task Dashboard.
 * <p>
 * Responsibilities:
 * - Load the primary FXML layout and CSS theme
 * - Wire the main controller to the root node (for cross-controller interactions)
 * - Initialize the database schema on startup (idempotent)
 * <p>
 * Tip: If the app starts without styles, check the classpath for the CSS path.
 */
package com.example.taskdashboardjava;

import com.example.taskdashboardjava.controller.Controller;
import com.example.taskdashboardjava.database.SetupDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Dashboard extends Application {
    /**
     * Starts the JavaFX stage and shows the main dashboard window.
     *
     * @param stage the primary application window
     * @throws IOException if the FXML cannot be loaded (usually a path issue)
     */
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/taskdashboardjava/FXML/Main.fxml")));
            Parent root = loader.load();
            Controller controller = loader.getController();
            root.setUserData(controller);
            Scene scene = new Scene(root , 1000, 600 );
            String cssFile = Objects.requireNonNull(this.getClass().getResource("/com/example/taskdashboardjava/CSS/Application.css")).toExternalForm();
            scene.getStylesheets().add(cssFile);
            Image icon = new Image(Objects.requireNonNull(
                    getClass().getResource("/com/example/taskdashboardjava/Images/Icon.png")).toExternalForm()
            );
            stage.getIcons().add(icon);
            stage.setTitle("Task Dashboard");
            stage.setMinWidth(800);
            stage.setMinHeight(500);
            stage.setScene(scene);
            stage.show();
            SetupDatabase.createTables();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}