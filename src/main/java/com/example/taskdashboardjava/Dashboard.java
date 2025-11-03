package com.example.taskdashboardjava;

//javaFx imports
import com.example.taskdashboardjava.controller.Controller; // <-- ADD THIS IMPORT
import com.example.taskdashboardjava.database.SetupDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//java util imports
import java.io.IOException;
import java.util.Objects;

public class Dashboard extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            // --- THIS IS THE FIX ---
            // 1. We need to use an FXMLLoader instance to get the controller
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/taskdashboardjava/FXML/Main.fxml")));
            Parent root = loader.load();

            // 2. Get the controller from the loader
            Controller controller = loader.getController();

            // 3. Store the controller in the root node so we can find it later
            root.setUserData(controller);
            // --- END FIX ---


            Scene scene = new Scene(root , 1000, 600 );

            //If the css file is used in more than one scene
            String cssFile = Objects.requireNonNull(this.getClass().getResource("/com/example/taskdashboardjava/CSS/Application.css")).toExternalForm();
            scene.getStylesheets().add(cssFile);

            //setting the Image Icon for the window
            Image icon = new Image(Objects.requireNonNull(
                    getClass().getResource("/com/example/taskdashboardjava/Images/Icon.png")).toExternalForm()
            );
            stage.getIcons().add(icon);
            stage.setTitle("Task Dashboard");
            stage.setMinWidth(800);
            stage.setMinHeight(500);
            stage.setScene(scene);
            stage.show();

            // database connection
            SetupDatabase.createTables();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}