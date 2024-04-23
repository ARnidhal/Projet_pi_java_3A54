module Test{
    requires javafx.controls;
    requires javafx.fxml;
    requires  javafx.graphics;
    requires itextpdf;
    requires java.sql;
    requires jakarta.mail;
    requires org.json;
    requires spring.security.core;


    requires java.net.http;
    opens controllers;
    opens models;
    opens services;
    opens test;
        }