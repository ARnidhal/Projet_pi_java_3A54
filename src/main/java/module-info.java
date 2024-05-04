module Test{
    requires javafx.controls;
    requires javafx.fxml;
    requires  javafx.graphics;
    requires java.sql;
    requires itextpdf;
    requires twilio;
    requires stripe.java;
    //requires kernel;
    //requires layout;
    //requires twilio;
    opens Controllers;
    opens com.visita.models;
    opens com.visita.services;
    opens com.visita.tests;
}