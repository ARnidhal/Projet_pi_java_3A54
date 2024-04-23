package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormReclamationController {

    @FXML
    private TextField nameTextField;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private TextField subjectTextField;
    @FXML
    private TextArea descriptionTextArea;

    // Define minimum and maximum lengths for each field
    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 50;
    private static final int SUBJECT_MIN_LENGTH = 5;
    private static final int SUBJECT_MAX_LENGTH = 100;
    private static final int DESCRIPTION_MIN_LENGTH = 10;
    private static final int DESCRIPTION_MAX_LENGTH = 500;

    @FXML
    public void initialize() {
        // Populate the categoryComboBox with predefined category choices
        categoryComboBox.getItems().addAll("Technical", "Customer Service", "Billing", "Product Quality");
        // Optionally, you can set a default value or prompt text
        categoryComboBox.setPromptText("Select Category");
    }

    @FXML
    private void submitReclamation() {
        String nom = nameTextField.getText().trim();
        String categorie = categoryComboBox.getValue();
        String sujet = subjectTextField.getText().trim();
        String description = descriptionTextArea.getText().trim();

        // Perform input validation
        if (nom.isEmpty() || categorie == null || sujet.isEmpty() || description.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
        } else if (nom.length() < NAME_MIN_LENGTH || nom.length() > NAME_MAX_LENGTH) {
            showAlert(Alert.AlertType.ERROR, "Error", "Name must be between " + NAME_MIN_LENGTH + " and " + NAME_MAX_LENGTH + " characters.");
        } else if (sujet.length() < SUBJECT_MIN_LENGTH || sujet.length() > SUBJECT_MAX_LENGTH) {
            showAlert(Alert.AlertType.ERROR, "Error", "Subject must be between " + SUBJECT_MIN_LENGTH + " and " + SUBJECT_MAX_LENGTH + " characters.");
        } else if (description.length() < DESCRIPTION_MIN_LENGTH || description.length() > DESCRIPTION_MAX_LENGTH) {
            showAlert(Alert.AlertType.ERROR, "Error", "Description must be between " + DESCRIPTION_MIN_LENGTH + " and " + DESCRIPTION_MAX_LENGTH + " characters.");
        } else {
            // Optionally, you can perform additional validation here if needed
            // If validation passes, proceed with creating the reclamation
            CreateReclamationController createReclamationController = new CreateReclamationController();
            createReclamationController.createReclamation(nom, categorie, sujet, description);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
