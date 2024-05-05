package com.example.visitajava.Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/com/example/visitajava/AddEvent.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setHeight(600); // Set your desired height
            primaryStage.setWidth(800);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML file", e);
        }
    }

}