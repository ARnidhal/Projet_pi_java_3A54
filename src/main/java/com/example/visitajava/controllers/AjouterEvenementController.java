
package com.example.visitajava.controllers;

import com.example.visitajava.Entity.Evenement;
import com.example.visitajava.Services.ServiceCategoryEvent;
import com.example.visitajava.Services.ServiceEvenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterEvenementController implements Initializable {

    @FXML
    private Button AjouterBtn;

    @FXML
    private ChoiceBox<Integer> categoryId;

    @FXML
    private DatePicker dateDebut;

    @FXML
    private DatePicker dateFin;

    @FXML
    private Button afficherEventBtn;
    @FXML
    private Button imageBtn;

    @FXML
    private TextField lieuEvenement;

    @FXML
    private TextField nbbParticipants;

    @FXML
    private TextField nomEvenement;

    @FXML
    private TextField typeEvenement;

    private String imagePath;
    private final ServiceEvenement SE = new ServiceEvenement();
    private final ServiceCategoryEvent SC = new ServiceCategoryEvent();
    @FXML
    void OnClickedAjouterEvent(ActionEvent event) throws SQLException {
        if (champsSontValides()) {
            Integer selectedCategoryId = categoryId.getValue();
            if (selectedCategoryId != null) {
                SE.ajouter(new Evenement(
                        nomEvenement.getText(),
                        imagePath,
                        lieuEvenement.getText(),
                        dateDebut.getValue().atStartOfDay(),
                        dateFin.getValue().atStartOfDay(),
                        typeEvenement.getText(),
                        Integer.parseInt(nbbParticipants.getText()),
                        selectedCategoryId
                ));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Événement ajouté");
                alert.setHeaderText(null);
                alert.setContentText("L'événement a été ajouté avec succès!");
                alert.showAndWait();
                clearFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de sélection");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner une catégorie!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs!");
            alert.showAndWait();
        }
    }

    private boolean champsSontValides() {
        // Check if any of the fields are empty or null
        if (nomEvenement.getText().isEmpty() ||
                typeEvenement.getText().isEmpty() ||
                lieuEvenement.getText().isEmpty() ||
                dateDebut.getValue() == null ||
                dateFin.getValue() == null ||
                imagePath == null ||
                nbbParticipants.getText().isEmpty() ||
                categoryId.getValue() == null) {
            return false; // Return false if any field is empty or null
        }
        return true; // All fields are filled, return true
    }
    @FXML
    void OnClickedAjouterImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        // Filtrer uniquement les fichiers d'image
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Récupérer le chemin absolu de l'image sélectionnée
            imagePath = selectedFile.getAbsolutePath();
            // Traiter l'image sélectionnée, par exemple, afficher le chemin dans un champ texte
            System.out.println("Chemin de l'image sélectionnée : " + imagePath);
        }
    }

    private void clearFields() {
      //  imageBtn.setValue(null);
        nomEvenement.clear();
        typeEvenement.clear();
        lieuEvenement.clear();
        dateDebut.setValue(null);
        dateFin.setValue(null);
        nbbParticipants.clear();
        categoryId.setValue(null);
        imagePath = null; // Réinitialiser le chemin de l'image après l'ajout de l'événement
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Integer> list = SC.listevenement();
        System.out.println(list);

        ObservableList<Integer> observableList = FXCollections.observableArrayList(list);
        categoryId.setItems(observableList);
    }


    @FXML
    void OnClickedAfficherEvent(ActionEvent event) {
        SC.changeScreen(event, "/com/example/visitajava/AfficherEvenementFront.fxml", "afficher back event front");

    }
}




