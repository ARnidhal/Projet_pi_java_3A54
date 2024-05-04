package Controllers;

import com.visita.models.Rendezvous;
import com.visita.services.RendezvousService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class AjouterRV1 implements Initializable {

    @FXML
    private Button btnclear;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private DatePicker dateid;

    @FXML
    private TextField emailid;

    @FXML
    private TextField fullnameid;

    @FXML
    private TextField medecinid;

    @FXML
    private TextField noteid;

    @FXML
    private TextField idrendezvous;

    @FXML
    private CheckBox etat;
    @FXML
    private TextField rapportid;








    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private TextField telid;

    @FXML
    private TextField timeid;
    @FXML
    private TableView<Rendezvous> tableView;
    RendezvousService ps = new RendezvousService();




    @FXML
    void addrv(ActionEvent event) throws ParseException {

        try {
            // Conversion de la date de type String en objet Date
            // Obtenez la valeur sélectionnée du DatePicker
            LocalDate selectedDate = dateid.getValue();

            // Convertissez la LocalDate en String
            String dateString = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Parsez la String en objet Date
            Date parsedDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);

            // Conversion de l'heure de type String en objet Time
            Time parsedTime = Time.valueOf(timeid.getText());

            // Conversion de la chaîne de caractères "true" ou "false" en booléen

            // Ajout du rendez-vous en utilisant la méthode ajouter de votre service
            ps.ajouter(new Rendezvous(
                            Integer.parseInt(medecinid.getText()), // medecin_id (supposant qu'il s'agit d'un champ texte)
                            fullnameid.getText(), // fullname (supposant qu'il s'agit d'un champ texte)
                            Integer.parseInt(telid.getText()), // tel (supposant qu'il s'agit d'un champ texte)
                            parsedDate, // date (convertie de la chaîne de caractères de votre champ de texte)
                            parsedTime, // time (convertie de la chaîne de caractères de votre champ de texte)
                            noteid.getText(), // note (supposant qu'il s'agit d'un champ texte)
                            // etat (converti de la valeur de la checkbox)
                            emailid.getText()// email (supposant qu'il s'agit d'un champ texte)
                    ) // rapport_id (supposant qu'il s'agit d'un champ texte)
            );

            // Afficher une alerte pour indiquer que le rendez-vous a été ajouté avec succès
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Rendez-vous ajouté avec succès !");
        } catch (ParseException e) {
            // Gérer l'erreur de parsing de la date ou de l'heure
            e.printStackTrace();

            // Afficher une alerte en cas d'échec de l'ajout du rendez-vous
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'ajout du rendez-vous !");
        }
    }

    @FXML
    void clearrv(ActionEvent event)
    {
        medecinid.clear();
        fullnameid.clear();
        telid.clear();
        dateid.setValue(null); // Effacer la date sélectionnée
        timeid.clear();
        noteid.clear();
        emailid.clear();
        idrendezvous.clear();

    }



    @FXML
    void updaterv() {


        try {
            // Conversion de la date de type String en objet Date
            // Obtenez la valeur sélectionnée du DatePicker
            LocalDate selectedDate = dateid.getValue();

            // Convertissez la LocalDate en String
            String dateString = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Parsez la String en objet Date
            Date parsedDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);

            // Conversion de l'heure de type String en objet Time
            Time parsedTime = Time.valueOf(timeid.getText());

            // Création d'un objet Rendezvous avec les données mises à jour
            Rendezvous rendezvous = new Rendezvous(Integer.parseInt(idrendezvous.getText()),
                    Integer.parseInt(medecinid.getText()), // medecin_id (supposant qu'il s'agit d'un champ texte)
                    fullnameid.getText(), // fullname (supposant qu'il s'agit d'un champ texte)
                    Integer.parseInt(telid.getText()), // tel (supposant qu'il s'agit d'un champ texte)
                    parsedDate, // date (convertie de la chaîne de caractères de votre champ de texte)
                    parsedTime, // time (convertie de la chaîne de caractères de votre champ de texte)
                    noteid.getText(), // note (supposant qu'il s'agit d'un champ texte)
                    // etat (converti de la valeur de la checkbox)
                    emailid.getText() // email (supposant qu'il s'agit d'un champ texte)
            ); // rapport_id (supposant qu'il s'agit d'un champ texte)

            // Ajout du rendez-vous en utilisant la méthode modifier de votre service
            ps.modifier(rendezvous);

            // Afficher une alerte pour indiquer que le rendez-vous a été mis à jour avec succès
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Rendez-vous mis à jour avec succès !");
        } catch (ParseException e) {
            // Gérer l'erreur de parsing de la date ou de l'heure
            e.printStackTrace();

            // Afficher une alerte en cas d'échec de la mise à jour du rendez-vous
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la mise à jour du rendez-vous !");
        }

    }


    private void showAlert(Alert.AlertType type, String title, String message)
        {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void next(ActionEvent event) throws IOException {

        next();


    }



    void  next() throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherRV.fxml"));
        Parent root=loader.load();

        medecinid.getScene().setRoot(root);
    }
}
