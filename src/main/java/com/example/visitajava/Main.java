package com.example.visitajava;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Load Font Awesome font
        Font.loadFont(getClass().getResourceAsStream("/fonts/fontawesome-webfont.ttf"), 12);

        // Create a sample text with a Font Awesome icon
        Text icon = new Text("\uf135"); // Example icon code for Font Awesome
        icon.setFont(Font.font("FontAwesome", 24)); // Set the font family and size

        // Create a Group to hold the Text node
        Group root = new Group(icon);

        // Create a Scene and set the root node
        Scene scene = new Scene(root, 100, 100);

        // Set the scene for the stage and show it
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
