package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResponseFormController {

    @FXML
    private TextArea responseTextArea;

    private Reclamation reclamation;

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }

    @FXML
    private void handleReply() {
        String responseContent = responseTextArea.getText();

        // Validate input
        if (responseContent.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Response content cannot be empty.");
        } else if (responseContent.length() > 500) { // Example: Limit response content to 500 characters
            showAlert(Alert.AlertType.ERROR, "Error", "Response content cannot exceed 500 characters.");
        } else {
            saveResponseToDatabase(responseContent);
            closeWindow();
        }
    }

    @FXML
    private void handleClose() {
        closeWindow();
    }

    private void saveResponseToDatabase(String responseContent) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO reponse (reclamation_id, author, response_content, response_date) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, reclamation.getId());
                statement.setString(2, "Admin");
                statement.setString(3, responseContent);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save response to the database.");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) responseTextArea.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
