package com.visita.controllers;

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
        loadView("/FormReclamation.fxml", "Créer Réclamation");
    }

    @FXML
    private void showShowReclamationView() throws IOException {
        // Load the user input view to get name and surname first
        loadNomPrenomForm();
    }

    @FXML
    private void showDeleteReclamationView() {
        loadView("/DeleteReclamation.fxml", "Supprimer Réclamation");
    }

    @FXML
    private void showUpdateReclamationView() {
        loadView("/UpdateReclamation.fxml", "Mettre à Jour Réclamation");
    }

    @FXML
    private void showStatisticsView() {
        loadView("/StatisticsView.fxml", "Statistiques des Réclamations");
    }

    @FXML
    private void loadNomPrenomForm() throws IOException {
        // Charger le fichier FXML de NomPrenomForm.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NomPrenomForm.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle instance du contrôleur NomPrenomFormController
        NomPrenomFormController controller = loader.getController();

        // Initialiser la référence à la scène dans NomPrenomFormController
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        // Passer la référence de la scène au contrôleur NomPrenomFormController
        controller.setStage(stage);

        // Afficher la scène
        stage.show();
    }

    void showReclamations(String nomPrenom) {
        // Load the ShowReclamation view with the given name and surname
        loadShowReclamationView(nomPrenom);
    }

    private void loadShowReclamationView(String nomPrenom) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowReclamation.fxml"));
            Parent root = loader.load();

            // Pass the name and surname to ShowReclamationController
            ShowReclamationController controller = loader.getController();
            controller.setNomPrenom(nomPrenom);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Afficher Réclamations");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
