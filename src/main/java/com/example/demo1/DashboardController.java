package com.example.demo1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    // Méthode pour naviguer vers la page de création de réclamation
    @FXML
    private void goToCreateReclamation(ActionEvent event) {
        // Implémentez la logique pour naviguer vers la page de création de réclamation
        // Par exemple, vous pouvez charger le fichier FXML de la page de création et afficher la scène correspondante
        // Vous pouvez utiliser FXMLLoader pour charger le FXML et Scene pour afficher la nouvelle scène
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateReclamation.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de la page de création de réclamation");
        }
    }

    // Méthode pour naviguer vers la page d'affichage de réclamation
    @FXML
    private void goToShowReclamation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowReclamation.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de la page d'affichage de réclamation");
        }
    }

    @FXML
    private void goToFormReclamation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FormReclamation.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de la page de formulaire de réclamation");
        }
    }


    // Méthode utilitaire pour afficher une boîte de dialogue d'alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

