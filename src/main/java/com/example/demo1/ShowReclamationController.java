package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowReclamationController {

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
    private Button backButton;


    @FXML
    public void initialize() {
        // Initialize columns
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        categorieColumn.setCellValueFactory(cellData -> cellData.getValue().categorieProperty());
        sujetColumn.setCellValueFactory(cellData -> cellData.getValue().sujetProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        subdateColumn.setCellValueFactory(cellData -> cellData.getValue().subdateProperty().asString());
        // Set custom cell factories for the buttons
        setupUpdateButtonColumn();
        setupDeleteButtonColumn();
        setupViewResponsesButtonColumn();
        // Load reclamations
        loadReclamations();
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        // Call a method to refresh the table data
        refreshTable();
    }

    private void refreshTable() {
        // Implement code to refresh the table data
        loadReclamations();
    }

    private void setupUpdateButtonColumn() {
        TableColumn<Reclamation, Void> updateColumn = new TableColumn<>("Modifier");
        updateColumn.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("Modifier");

            {
                updateButton.setOnAction(event -> {
                    Reclamation reclamation = getTableView().getItems().get(getIndex());
                    openUpdateView(reclamation);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });

        reclamationTableView.getColumns().add(updateColumn);
    }

    private void setupDeleteButtonColumn() {
        TableColumn<Reclamation, Void> deleteColumn = new TableColumn<>("Supprimer");
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    Reclamation reclamation = getTableView().getItems().get(getIndex());
                    deleteReclamation(reclamation);
                    reclamationTableView.getItems().remove(reclamation);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        reclamationTableView.getColumns().add(deleteColumn);
    }

    public void loadReclamations() {
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

    private void deleteReclamation(Reclamation reclamation) {
        String sql = "DELETE FROM reclamation WHERE id=?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reclamation.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openUpdateView(Reclamation reclamation) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateReclamation.fxml"));
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(loader.load()));
            UpdateReclamationController controller = loader.getController();
            controller.setReclamationToUpdate(reclamation);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setupViewResponsesButtonColumn() {
        TableColumn<Reclamation, Void> viewResponsesColumn = new TableColumn<>("Voir Réponses");
        viewResponsesColumn.setCellFactory(param -> new TableCell<>() {
            private final Button viewResponsesButton = new Button("Voir Réponses");

            {
                viewResponsesButton.setOnAction(event -> {
                    Reclamation reclamation = getTableView().getItems().get(getIndex());
                    openViewResponsesView(reclamation);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(viewResponsesButton);
                }
            }
        });

        reclamationTableView.getColumns().add(viewResponsesColumn);
    }
    private void openViewResponsesView(Reclamation reclamation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewResponses.fxml"));
            Parent root = loader.load();

            ViewResponsesController controller = loader.getController();
            controller.setReclamation(reclamation);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Réponses pour la Réclamation: " + reclamation.getId());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleBack() {
        try {
            // Load the FXML file of the main scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
            Parent mainRoot = loader.load();

            // Create a new scene with the content of the main scene
            Scene mainScene = new Scene(mainRoot);

            // Get the main stage from the current scene
            Stage mainStage = (Stage) reclamationTableView.getScene().getWindow();

            // Set the main scene on the main stage
            mainStage.setScene(mainScene);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if the FXML file cannot be loaded
        }

    }
    @FXML
    private void goToMainView(ActionEvent event) {
        // Implement the logic to navigate back to the main view
        // You can load the main view FXML and set it as the scene
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}




