package com.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void showCreateReclamationView() {
        loadView("FormReclamation.fxml", "Créer Réclamation");
    }

    @FXML
    private void showShowReclamationView() {
        loadView("ShowReclamation.fxml", "Afficher Réclamations");
    }

    @FXML
    private void showDeleteReclamationView() {
        loadView("DeleteReclamation.fxml", "Supprimer Réclamation");
    }

    @FXML
    private void showUpdateReclamationView() {
        loadView("UpdateReclamation.fxml", "Mettre à Jour Réclamation");
    }

    private void loadView(String fxmlFileName, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            Stage stage = new Stage(); // Create a new stage for the new view
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
