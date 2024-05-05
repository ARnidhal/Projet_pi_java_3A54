package com.example.visitajava.Test;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class HelloController {

    @FXML
    private Label welcomeText;

    // Method to handle button click event
    @FXML
    private void onHelloButtonClick() {
        welcomeText.setText("Hello, JavaFX!");
    }

}
