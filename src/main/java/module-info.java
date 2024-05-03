module Test{
    requires javafx.controls;
    requires javafx.fxml;
    requires  javafx.graphics;
    requires java.sql;
    requires jakarta.mail;
    requires org.json;
    requires itextpdf;
    requires twilio;
    requires spring.security.core;
    opens com.visita.controllers;
    opens com.visita.models;
    opens com.visita.services;
    opens com.visita.test;
}