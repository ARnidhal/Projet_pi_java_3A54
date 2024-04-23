package com.example.demo1;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminReclamationController {

    @FXML
    private TableView<Reclamation> reclamationTableView;

    @FXML
    private TableColumn<Reclamation, Integer> idColumn;

    @FXML
    private TableColumn<Reclamation, String> nomColumn;

    @FXML
    private TableColumn<Reclamation, String> categorieColumn;

    @FXML
    private TableColumn<Reclamation, String> sujetColumn;

    @FXML
    private TableColumn<Reclamation, String> descriptionColumn;

    @FXML
    private TableColumn<Reclamation, String> subdateColumn;

    @FXML
    private TableColumn<Reclamation, Void> responseColumn;



    @FXML
    public void initialize() {
        // Initialize columns
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        categorieColumn.setCellValueFactory(cellData -> cellData.getValue().categorieProperty());
        sujetColumn.setCellValueFactory(cellData -> cellData.getValue().sujetProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        subdateColumn.setCellValueFactory(cellData -> cellData.getValue().subdateProperty().asString());

        // Set custom cell factory for the response button column
        setupResponseButtonColumn();

        // Load reclamations
        loadReclamations();
    }

    private void setupResponseButtonColumn() {
        responseColumn.setCellFactory(param -> new TableCell<>() {
            private final Button responseButton = new Button("Répondre");

            {
                responseButton.setOnAction(event -> {
                    Reclamation reclamation = getTableView().getItems().get(getIndex());
                    openResponseForm(reclamation);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(responseButton);
                }
            }
        });
    }

    private void loadReclamations() {
        ObservableList<Reclamation> reclamationList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM reclamation";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(resultSet.getInt("id"));
                reclamation.setNom(resultSet.getString("nom"));
                reclamation.setCategorie(resultSet.getString("categorie"));
                reclamation.setSujet(resultSet.getString("sujet"));
                reclamation.setDescription(resultSet.getString("description"));
                reclamation.setSubdate(resultSet.getTimestamp("subdate").toLocalDateTime());
                reclamationList.add(reclamation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        reclamationTableView.setItems(reclamationList);
    }
    @FXML
    private void openResponseForm(Reclamation reclamation) {
        try {
            // Load the FXML view of the response form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ResponseForm.fxml"));
            Parent root = loader.load();

            // Get the controller of the response form
            ResponseFormController controller = loader.getController();

            // Pass the selected reclamation to the response form controller
            controller.setReclamation(reclamation);

            // Create a new scene with the response form and display it in a new window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Respond to Reclamation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void respondToReclamation(ActionEvent event) {
        Reclamation selectedReclamation = reclamationTableView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            openResponseForm(selectedReclamation);
        } else {
            // Handle case where no reclamation is selected
        }
    }
    @FXML
    private void openResponseView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminReclamation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Reclamation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
