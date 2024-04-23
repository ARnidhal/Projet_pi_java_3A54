module Test{
    requires javafx.controls;
    requires javafx.fxml;
    requires  javafx.graphics;
    requires java.sql;
    requires itextpdf;
    requires twilio;
    //requires kernel;
    //requires layout;
    //requires twilio;
    opens Controllers;
    opens com.esprit.java.models;
    opens com.esprit.java.services;
    opens com.esprit.java.tests;
}