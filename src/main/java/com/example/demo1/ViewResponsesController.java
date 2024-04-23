package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewResponsesController {

    @FXML
    private TextArea responsesTextArea;

    private Reclamation reclamation;

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
        loadResponses();
    }

    private void loadResponses() {
        // Fetch responses associated with the reclamation ID from the database
        StringBuilder responsesText = new StringBuilder();
        String sql = "SELECT * FROM reponse WHERE reclamation_id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reclamation.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    responsesText.append("Response ID: ").append(resultSet.getInt("id")).append("\n");
                    responsesText.append("Author: ").append(resultSet.getString("author")).append("\n");
                    responsesText.append("Content: ").append(resultSet.getString("response_content")).append("\n\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set the responses text in the TextArea and make it read-only
        responsesTextArea.setText(responsesText.toString());
        responsesTextArea.setEditable(false);
    }
}
