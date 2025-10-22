package com.example.taskdashboardjava;

//javaFx imports
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
           //get root if the file is not null
           Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
           Scene scene = new Scene(root , 1000, 600 );

           //If the css file is used in more than one scene
           String cssFile = Objects.requireNonNull(this.getClass().getResource("/Application.css")).toExternalForm();
           scene.getStylesheets().add(cssFile);

           //if it is only having one css location
           //scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Application.css")).toExternalForm());

           //setting the Image Icon for the window
           Image icon = new Image("Icon.png");
           stage.getIcons().add(icon);
           stage.setTitle("Task Dashboard");
           stage.setMinWidth(800);
           stage.setMinHeight(500);
           stage.setScene(scene);
           stage.show();
       }
       catch (Exception e){
           e.printStackTrace();
       }
    }

}
