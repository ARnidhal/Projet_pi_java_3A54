module Test{
    requires javafx.controls;
    requires javafx.fxml;
    requires  javafx.graphics;
    requires java.sql;
    requires org.json;
    requires itextpdf;
    requires twilio;
    requires spring.security.core;
    // cmu.us.kal;
    requires freetts;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires java.mail;
    requires com.google.api.client;
    requires org.apache.commons.text;
    opens com.visita.controllers;
    opens com.visita.models;
    opens com.visita.services;
    opens com.visita.test;
}

