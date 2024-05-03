package com.visita.test;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.image.Image;

public class MainFx  extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
//AddCategory
//AddService
//AfficherService
//backresevservice



        FXMLLoader loader=new FXMLLoader(getClass().getResource("/AddCategory" +
                ".fxml"));
        Parent root=loader.load();
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajouter Service");
        primaryStage.show();
        //primaryStage.setMaximized(true);


    }


    public static void main(String[] args) {



        launch(args);
    }
}