package com.example.assignment3;


import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.View;

import java.io.IOException;

/**
 * <H1>Record Application</H1>
 * The main class of the application
 */
public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        // create the view2

        View view = new View();
        view.viewStartUp();
        // Start the scene
        stage.setScene(new Scene(view.getRoot(), 600, 700));
        stage.getIcons().add(new Image("file:files/icon.jpg"));
        stage.setTitle("MTU Student Record System");
        stage.show();
    }


}