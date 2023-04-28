package com.example.assignment3;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import control.RecordView;

import java.io.IOException;
public class RecordApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // create the view2

        RecordView view = new RecordView();
        view.viewStartUp();
        // Start the scene
        stage.setScene(new Scene(view.getRoot(),600,700));
        stage.getIcons().add(new Image("file:files/icon.jpg"));
        stage.setTitle("MTU Student Record System");
        stage.show();
    }



}