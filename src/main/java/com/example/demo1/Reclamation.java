package com.example.demo1;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Reclamation {

    private final IntegerProperty id;
    private final StringProperty nom;
    private final StringProperty categorie;
    private final StringProperty sujet;
    private final StringProperty description;
    private final ObjectProperty<LocalDateTime> subdate;



    public Reclamation() {
        this.id = new SimpleIntegerProperty();
        this.nom = new SimpleStringProperty();
        this.categorie = new SimpleStringProperty();
        this.sujet = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.subdate = new SimpleObjectProperty<>();
    }

    // Getters and setters for id
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    // Getters and setters for nom
    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    // Getters and setters for categorie
    public String getCategorie() {
        return categorie.get();
    }

    public void setCategorie(String categorie) {
        this.categorie.set(categorie);
    }

    public StringProperty categorieProperty() {
        return categorie;
    }

    // Getters and setters for sujet
    public String getSujet() {
        return sujet.get();
    }

    public void setSujet(String sujet) {
        this.sujet.set(sujet);
    }

    public StringProperty sujetProperty() {
        return sujet;
    }

    // Getters and setters for description
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    // Getters and setters for subdate
    public LocalDateTime getSubdate() {
        return subdate.get();
    }

    public void setSubdate(LocalDateTime subdate) {
        this.subdate.set(subdate);
    }

    public ObjectProperty<LocalDateTime> subdateProperty() {
        return subdate;
    }
}
