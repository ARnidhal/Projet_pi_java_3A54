package Controllers;

import com.esprit.java.models.Rapport;
import com.esprit.java.models.Rendezvous;
import com.esprit.java.services.RapportService;
import com.esprit.java.services.RendezvousService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class FrontRV {

    @FXML
    private Button btnafficher;

    @FXML
    private VBox chosenFruitCard;

    @FXML
    private TextField idrendezvous;

    @FXML
    private Button logoutButton;

    @FXML
    private TextArea rapportT;

    private RendezvousService rendezvousService = new RendezvousService();
    private RapportService rapportService = new RapportService();

    @FXML
    void afficherRP(ActionEvent event)
    {
        // Récupérer l'identifiant du rendez-vous depuis le champ de texte
        int rendezvousId = Integer.parseInt(idrendezvous.getText());

        // Appeler le service pour récupérer le rendez-vous par son ID
        Rendezvous rendezvous = rendezvousService.getById(rendezvousId);

        // Vérifier si le rendez-vous existe
        if (rendezvous != null) {
            // Accéder au rapport associé au rendez-vous
            int rapportId = rendezvous.getRapport_id();

            // Vérifier si le rapport existe
            if (rapportId == 0 ) {
                // Le rendez-vous n'a pas de rapport associé
                showAlert(Alert.AlertType.INFORMATION, "Information", "Aucun rapport trouvé", "Ce rendez-vous n'a pas de rapport associé.");
                rapportT.setText("Aucun rapport associé a ce rendezvous pour le moment ");
            } else {
                // Le rendez-vous a un rapport associé, récupérer les données du rapport
                Rapport rapport = rapportService.getById(rapportId);

                // Vérifier si le rapport existe
                if (rapport != null) {
                    // Afficher les attributs du rapport dans le TextArea
                    String rapportDetails = "VOTRE RAPPORT : \n"+"Type:\t " + rapport.getType() + "\n" +
                            "Note:\t " + rapport.getNote() + "\n"
                            ;
                    rapportT.setText(rapportDetails);
                } else {
                    // Le rapport n'existe pas
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Rapport introuvable", "Le rapport associé à ce rendez-vous n'existe pas.");
                }
            }
        } else {
            // Le rendez-vous n'existe pas
            showAlert(Alert.AlertType.ERROR, "Erreur", "Rendez-vous introuvable", "Aucun rendez-vous trouvé avec cet identifiant.");
        }

    }

    @FXML
    void handleLogoutButton(ActionEvent event) {

    }


    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


}
