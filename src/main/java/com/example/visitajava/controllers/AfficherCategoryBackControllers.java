package com.example.visitajava.controllers;

import com.example.visitajava.Entity.CategoryEvent;
import com.example.visitajava.Services.ServiceCategoryEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherCategoryBackControllers implements Initializable {

    @FXML
    private Button suppCategoryBtn;
    @FXML
    private Button modifierCategoryBtn;

    @FXML
    private ScrollPane ScrollPane;

    @FXML
    private TextField Descfield;

    @FXML
    private TextField nomfield;

    @FXML
    private VBox VboxCategory;

    private final ServiceCategoryEvent SC = new ServiceCategoryEvent();
    private CategoryEvent  selectedCategory;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refreshEvents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshEvents() throws SQLException {
        List<CategoryEvent> categoryEvents = SC.afficher();
        VboxCategory.getChildren().clear();
        for (CategoryEvent categoryEvent : categoryEvents) {

            Label eventLabel = new Label(
                    "Nom: " + categoryEvent.getName() +
                            ", Description: " + categoryEvent.getDescription()
            );
            eventLabel.setOnMouseClicked(event -> {
                selectedCategory = categoryEvent;
                nomfield.setText(selectedCategory.getName());
                Descfield.setText(selectedCategory.getDescription());

            });

            Separator separator = new Separator(Orientation.HORIZONTAL);

            VboxCategory.getChildren().addAll(eventLabel, separator);
        }
    }


    @FXML
    void OnClickedModifierCategory(ActionEvent event) throws SQLException {
        if (selectedCategory != null) {
            selectedCategory.setName(nomfield.getText());
            selectedCategory.setDescription(Descfield.getText());
            SC.modifier(selectedCategory);
            refreshEvents();
        }
    }

    @FXML
    void OnClickedSupprimerCategory(ActionEvent event) throws SQLException {
        if (selectedCategory != null) {
            int res = selectedCategory.getId();
            SC.supprimer(new CategoryEvent(res, nomfield.getText(),Descfield.getText()));
            refreshEvents();
        }
    }

}


