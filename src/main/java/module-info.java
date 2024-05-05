module test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;
    requires java.mail;
    requires itextpdf;
    requires java.desktop;




    opens com.visita.controllers;
    opens com.visita.utils ;
    opens com.visita.test ;
    opens com.visita.models ;
    opens com.visita.services ;
}