module Test{
    requires javafx.controls;
    requires javafx.fxml;
    requires  javafx.graphics;
    requires java.sql;
    requires itextpdf;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires java.mail;
    requires com.google.api.client;
    opens com.visita.Controllers;
    opens com.visita.models;
    opens com.visita.services;
    opens com.visita.test;

}
