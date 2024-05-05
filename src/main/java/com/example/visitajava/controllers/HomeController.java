package com.example.visitajava.controllers;

import com.example.visitajava.Entity.Evenement;
import com.example.visitajava.Services.ServiceEvenement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HomeController {
    @FXML
    private TextField search;
    @FXML
    private HBox content;

    @FXML
    private Button nextPageButton;

    @FXML
    private Label pageNumberLabel;

    @FXML
    private Button prevPageButton;

    private final ServiceEvenement serviceEvenement;
    private int currentPage = 1;
    private int pageSize = 2; // Adjust the page size as needed

    public HomeController() {
        this.serviceEvenement = new ServiceEvenement();
    }

    @FXML
    private void initialize() {
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() || newValue == null) {
                currentPage = 1;
                loadEventItems();
            } else {
                searchEvents(newValue);
            }
        });
        loadEventItems();
    }

    private void loadEventItems() {
        try {
            List<Evenement> evenements = serviceEvenement.getEventsForPage(currentPage, pageSize);
            content.getChildren().clear(); // Clear previous items
            for (Evenement evenement : evenements) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/visitajava/EventFront.fxml"));
                VBox item = loader.load();
                EventFrontController eventFrontController = loader.getController();
                eventFrontController.setData(evenement);
                content.getChildren().add(item);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void searchEvents(String keyword) {
        try {
            List<Evenement> evenements = serviceEvenement.searchEvents(keyword);
            content.getChildren().clear(); // Clear previous items
            for (Evenement evenement : evenements) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/visitajava/EventFront.fxml"));
                VBox item = loader.load();
                EventFrontController eventFrontController = loader.getController();
                eventFrontController.setData(evenement);
                content.getChildren().add(item);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void nextPage() {
        currentPage++;
        loadEventItems();
        updatePageNumberLabel();
    }

    @FXML
    private void prevPage() {
        if (currentPage > 1) {
            currentPage--;
            loadEventItems();
            updatePageNumberLabel();
        }
    }

    private void updatePageNumberLabel() {
        pageNumberLabel.setText(String.valueOf(currentPage));
    }
}