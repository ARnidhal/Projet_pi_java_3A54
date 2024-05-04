package com.visita.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Imc {

    @FXML
    private TextField weightTextField;

    @FXML
    private TextField heightTextField;

    @FXML
    private Label resultLabel;

    @FXML
    void calculateIMC() {
        try {
            double weight = Double.parseDouble(weightTextField.getText());
            double height = Double.parseDouble(heightTextField.getText());

            // Calculate BMI
            double bmi = weight / (height * height);

            // Determine BMI category
            String category;
            if (bmi < 18.5) {
                category = "Sous-poids";
                setCategoryStyle(category, bmi, "/imcR1.fxml");
            } else if (bmi >= 18.5 && bmi < 25) {
                category = "Poids normal";
                setCategoryStyle(category, bmi, "/imcR2.fxml");
            } else {
                category = "Surpoids";
                setCategoryStyle(category, bmi, "/imcR3.fxml");
            }

            // Display the result with appropriate color
            resultLabel.setText(String.format("Votre IMC est %.2f (%s)", bmi, category));

        } catch (NumberFormatException e) {
            resultLabel.setText("Veuillez entrer des valeurs valides pour le poids et la taille.");
            resultLabel.getStyleClass().clear(); // Clear previous styles
        }
    }

    private void setCategoryStyle(String category, double bmi, String fxmlFile) {
        resultLabel.getStyleClass().clear(); // Clear previous styles

        String idealWeightRange = "";
        if (category.equals("Sous-poids")) {
            resultLabel.getStyleClass().add("underweight");
            idealWeightRange = "Poids idéal: 18.5 - 24.9 kg/m²";
        } else if (category.equals("Poids normal")) {
            resultLabel.getStyleClass().add("normal-weight");
            idealWeightRange = "Poids idéal: 18.5 - 24.9 kg/m²";
        } else {
            resultLabel.getStyleClass().add("overweight");
            idealWeightRange = "Poids idéal: 18.5 - 24.9 kg/m²";
        }

        // Load the appropriate FXML
        loadFXML(fxmlFile);

        resultLabel.setText(String.format("Votre IMC est %.2f (%s)\n%s", bmi, category, idealWeightRange));
    }

    private void loadFXML(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
