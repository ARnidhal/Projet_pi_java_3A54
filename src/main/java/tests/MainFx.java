package tests;


import Controllers.Signup;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MainFx extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Signup.fxml"));
        loader.setControllerFactory(clazz -> new Signup(passwordEncoder));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    // Other methods




    public static void main(String[] args) {

        launch(args);
    }
}


