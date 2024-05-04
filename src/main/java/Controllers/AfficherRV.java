package Controllers;

import com.visita.models.Rapport;
import com.visita.models.Rendezvous;
import com.visita.services.RendezvousService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class AfficherRV {

    @FXML
    private Button btnback;

    @FXML
    private Button btndelete;


    @FXML
    private TableColumn<Rendezvous, Date> date;

    @FXML
    private TableColumn<Rendezvous, Integer> doctor;

    @FXML
    private TableColumn<Rendezvous, String> email;

    @FXML
    private TableColumn<Rendezvous, Boolean> etat;

    @FXML
    private TableColumn<Rendezvous, String> fullname;

    @FXML
    private TableColumn<Rendezvous, Integer> IDRV;

    @FXML
    private TableColumn<Rendezvous, String> note;

    @FXML
    private TableColumn<Rendezvous, Integer> phone;

    @FXML
    private TableColumn<Rendezvous, Rapport> report;

    @FXML
    private TableView<Rendezvous> tableview;

    @FXML
    private TableColumn<Rendezvous, Time> time;

    @FXML
    private TableView<Rendezvous> TableView;
    RendezvousService ps = new RendezvousService();

    @FXML
    void initialize() {


        IDRV.setCellValueFactory(new PropertyValueFactory<>("Id"));
        doctor.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
        fullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        phone.setCellValueFactory(new PropertyValueFactory<>("tel"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        note.setCellValueFactory(new PropertyValueFactory<>("note"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        report.setCellValueFactory(new PropertyValueFactory<>("rapport_id"));



        // Load countries into the table
        List<Rendezvous> liste = ps.afficher();
        tableview.getItems().addAll(liste);


    }
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void deleteRV() throws IOException {

        Rendezvous selectedRV = tableview.getSelectionModel().getSelectedItem();
        if (selectedRV != null) {
            ps.supprimer(selectedRV);
            tableview.getItems().remove(selectedRV);
            showAlert(Alert.AlertType.INFORMATION, "Success", "appointement deleted successfully!");
        } else {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select an appointement to delete.");
        }


    }
    @FXML
    void backPage (ActionEvent event) throws IOException {


        back1();

    }
    void  back1() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterRV1.fxml"));
        Parent root = loader.load();

        // Utilisez le bouton btnback pour obtenir sa scène parente
        Scene currentScene = btnback.getScene();

        // Remplacez la racine de la scène actuelle par la racine de la nouvelle vue chargée
        currentScene.setRoot(root);


    }






}
