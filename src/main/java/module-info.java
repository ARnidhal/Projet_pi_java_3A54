module Test{
    requires javafx.controls;
    requires javafx.fxml;
    requires  javafx.graphics;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires jakarta.mail;
    opens com.visita.Controllers;
    opens com.visita.models;
    opens com.visita.services;
    opens com.visita.test;

}
