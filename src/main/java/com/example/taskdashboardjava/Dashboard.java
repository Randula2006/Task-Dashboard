package com.example.taskdashboardjava;

//javaFx imports
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//java util imports
import java.io.IOException;

public class Dashboard extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        Image icon = new Image("Icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Task Dashboard");
        stage.setScene(scene);
        stage.show();
    }

}
