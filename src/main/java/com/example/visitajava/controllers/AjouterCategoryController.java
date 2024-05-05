package com.example.visitajava.controllers;

import com.example.visitajava.Entity.CategoryEvent;
import com.example.visitajava.Services.ServiceCategoryEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AjouterCategoryController {


    @FXML
    private Button AfficherBtn;

    @FXML
    private TextField Description;

    @FXML
    private Button ajouterBtnCategory;

    @FXML
    private TextField name;



    private final ServiceCategoryEvent SC = new ServiceCategoryEvent();

    @FXML
    void onClickedAjouterCategory(ActionEvent event) throws SQLException {

        if (champsSontValides()) {
            SC.ajouter(new CategoryEvent(name.getText(), Description.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("category ajouté");
            alert.setHeaderText(null);
            alert.setContentText("L'categories a été ajouté avec succès!");
            alert.showAndWait();
            clearFields();
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs!");
            alert.showAndWait();
        }

    }

    private boolean champsSontValides() {
        return !Description.getText().isEmpty() && !name.getText().isEmpty();
    }

    private void clearFields() {
        Description.clear();
        name.clear();

    }

    @FXML
    void OnClickedAfficherCategory(ActionEvent event) {
        SC.changeScreen(event, "/com/example/visitajava/AfficherCategoryBack.fxml", "afficher back category front");
    }

}
