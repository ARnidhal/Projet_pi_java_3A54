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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherEvenementBackController implements Initializable {

    @FXML
    private VBox EventVbox;

    @FXML
    private ChoiceBox<Integer> category_id;

    @FXML
    private DatePicker date_debut;

    @FXML
    private DatePicker date_fin;

    @FXML
    private TextField lieu_evenement;

    @FXML
    private Button modifierBtn;

    @FXML
    private TextField nb_participants;

    @FXML
    private TextField nom_evenement;

    @FXML
    private Button suppBtn;

    @FXML
    private TextField type_evenement;

    private final ServiceEvenement SE = new ServiceEvenement();
    private final ServiceCategoryEvent SC = new ServiceCategoryEvent();

    private Evenement selectedEvenement;
    private final String imageDirectory = "C:/Users/Chelly Emna/Desktop/Visita/public/uploads/";

    private String imagePath;
    private ServiceEvenement serviceEvenement;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceEvenement = new ServiceEvenement();

        try {
            populateCategoryChoiceBox();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            refreshReservations();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Integer> list = SC.listevenement(); // Correction du nom de méthode
        ObservableList<Integer> observableList = FXCollections.observableArrayList(list);
        category_id.setItems(observableList);
    }

    private void refreshReservations() throws SQLException {
        List<Evenement> evenements = SE.afficher();
        EventVbox.getChildren().clear();
        category_id.getItems().clear();

        for (Evenement evenement : evenements) {
            Label evenementLabel = new Label(
                    "Image: " + evenement.getImage_evenement() +
                    "Type: " + evenement.getType_evenement() +
                            ", Nom: " + evenement.getNom_evenement() +
                            ", Lieu: " + evenement.getLieu_evenement() +
                            ", Date début: " + evenement.getDate_debut() + // Correction de la concaténation
                            ", Date Fin: " + evenement.getDate_fin() +
                            ", nb de participant: " + evenement.getNb_participants() +
                            ", category Id: " + evenement.getCategory_id());

            ImageView imageView = new ImageView();
            try {
                File imageFile = new File("C:/Users/Chelly Emna/Desktop/Visita/public/uploads/" + evenement.getImage_evenement());
                javafx.scene.image.Image image = new Image(imageFile.toURI().toString());
                imageView.setImage(image);
                imageView.setFitWidth(100); // Set the desired width of the image
                imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image
            } catch (Exception e) {
                // Handle image loading errors
                System.err.println("Error loading image: " + e.getMessage());
                // Show error message in alert dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error loading image: " + e.getMessage());
                alert.showAndWait();
            }


            evenementLabel.setOnMouseClicked(event -> {
                selectedEvenement = evenement;

                type_evenement.setText(selectedEvenement.getType_evenement());
                nom_evenement.setText(selectedEvenement.getNom_evenement());
                lieu_evenement.setText(selectedEvenement.getLieu_evenement());
                date_debut.setValue(selectedEvenement.getDate_debut().toLocalDate());
                date_fin.setValue(selectedEvenement.getDate_fin().toLocalDate());
                nb_participants.setText(String.valueOf(selectedEvenement.getNb_participants()));
                //category_id.setValue(selectedEvenement.getCategory_id());
                category_id.setValue(evenement.getCategory_id());

            });

            //VBox eventBox = new VBox();
            EventVbox.getChildren().addAll(imageView, evenementLabel);
            category_id.getItems().add(evenement.getCategory_id());

           // EventVbox.getChildren().add(eventBox);
            //²category_id.getItems().add(evenement.getCategory_id());
        }
    }

   /* @FXML
    void onClickedModifierEvent(ActionEvent event) {
        if (selectedEvenement != null) {
            try {
                selectedEvenement.setType_evenement(type_evenement.getText());
                selectedEvenement.setNom_evenement(nom_evenement.getText());
                selectedEvenement.setLieu_evenement(lieu_evenement.getText());
                selectedEvenement.setDate_debut(date_debut.getValue().atStartOfDay());
                selectedEvenement.setDate_fin(date_fin.getValue().atStartOfDay());
                selectedEvenement.setNb_participants(Integer.parseInt(nb_participants.getText()));
                //selectedEvenement.setCategory_id(category_id.getValue());
                selectedEvenement.setCategory_id(category_id.getValue());

                SE.modifier(selectedEvenement);
                refreshReservations();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/

    private void populateCategoryChoiceBox() throws SQLException {
        List<Integer> list = SC.listevenement();
        ObservableList<Integer> observableList = FXCollections.observableArrayList(list);
        category_id.setItems(observableList);
    }
    @FXML
    void onClickedModifierEvent(ActionEvent event) {
        if (selectedEvenement != null) {
            try {
                // Update category ID separately if it changes
                int newCategoryId = category_id.getValue();
                if (newCategoryId != selectedEvenement.getCategory_id()) {
                    // Handle category ID update separately if it changes
                    // This might involve updating the choice box or any related UI elements
                    // If needed, update the event object with the new category ID
                    selectedEvenement.setCategory_id(newCategoryId);
                }

                // Update other event details
                selectedEvenement.setType_evenement(type_evenement.getText());
                selectedEvenement.setNom_evenement(nom_evenement.getText());
                selectedEvenement.setLieu_evenement(lieu_evenement.getText());
                selectedEvenement.setDate_debut(date_debut.getValue().atStartOfDay());
                selectedEvenement.setDate_fin(date_fin.getValue().atStartOfDay());
                selectedEvenement.setNb_participants(Integer.parseInt(nb_participants.getText()));

                // Call the modifier method from the service to update the event
                SE.modifier(selectedEvenement);

                // Refresh the displayed events after modification
                refreshReservations();

                // Repopulate the choice box with all category IDs
                populateCategoryChoiceBox();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    @FXML
    void onClickedSuppEvent(ActionEvent event) {
        if (selectedEvenement != null) {
            try {
                SE.supprimer(selectedEvenement);
                refreshReservations();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


