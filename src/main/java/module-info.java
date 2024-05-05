module com.example.visitajava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires itextpdf;
    requires java.mail;

    opens com.example.visitajava.Entity;

    opens com.example.visitajava.controllers;

    opens com.example.visitajava to javafx.fxml;
    //exports com.example.visitajava;
    exports com.example.visitajava.Test;
    exports com.example.visitajava.controllers;
    opens com.example.visitajava.Test to javafx.fxml;
}
