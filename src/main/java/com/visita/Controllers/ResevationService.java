package com.visita.Controllers;
import com.visita.models.ReservationService;
import com.visita.services.ReservationSrvService;
import jakarta.mail.MessagingException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.visita.models.Category;
import com.visita.models.Service;
import com.visita.services.Categoryservice;
import com.visita.services.ServiceService;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class ResevationService {
    @FXML
    private Label MessageLabel;

    @FXML
    private Button Reserver;

    @FXML
    private ImageView ServiceIMG;

    @FXML
    private TextField Service_Name;

    @FXML
    private TextField User_Email_resv;

    @FXML
    private TextField user_NAME_res;

    @FXML
    private TextField SERVICE_ID_res1;
    @FXML
    private Label messageLabel; // Déclaration de messageLabel

    private int serviceId;
    private String serviceName;
    private String serviceImageURL;
    private ServiceService serviceService;
    private ReservationSrvService reservationService;
    private Service selectedService; // Le service sélectionné

    public ResevationService() {
        reservationService = new ReservationSrvService();
    }


    // Méthode pour initialiser les données du service sélectionné
    // Méthode pour initialiser les données du service sélectionné
    @FXML
    public void initData(Service selectedService) {
        // Assurez-vous que selectedService n'est pas null
        if (selectedService != null) {
            // Utilisez les données du service sélectionné pour initialiser les éléments de l'interface utilisateur
            Service_Name.setText(selectedService.getNom());
            SERVICE_ID_res1.setText(String.valueOf(selectedService.getId()));

            // Charger et définir l'image
            String imageUrl = selectedService.getImage();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                ServiceIMG.setImage(new Image(imageUrl));
            }

            // Définir selectedService sur la valeur passée en paramètre
            this.selectedService = selectedService;
        } else {
            // Si selectedService est null, affichez un message d'erreur ou effectuez une autre action appropriée
            messageLabel.setText("Error: Selected service is null.");
        }
    }




    // Méthode pour gérer l'action de réservation
    // Méthode pour gérer l'action de réservation
    @FXML
    private void handleReservation(ActionEvent event) {
        // Vérifiez si selectedService est null avant de l'utiliser
        if (selectedService == null) {
            messageLabel.setText("Error: No service selected.");
            return;
        }

        String userName = user_NAME_res.getText();
        String userEmail = User_Email_resv.getText();

        // Validation des données
        if (userName.isEmpty() || userEmail.isEmpty()) {
            messageLabel.setText("Please fill in all required fields.");
            return;
        }

        // Validation de l'email (optionnel)
        if (!isValidEmail(userEmail)) {
            messageLabel.setText("Invalid email address.");
            return;
        }

        // Création de l'objet ReservationService à ajouter
        ReservationService reservation = new ReservationService(selectedService.getId(), userName, userEmail);

        // Ajout de la réservation
        reservationService.ajouter(reservation);

        // Affichage du message de succès
        messageLabel.setText("Reservation successful!");


        // Send email notification
        sendReservationNotification(selectedService.getNom(), userEmail);


    }


    // Méthode pour valider l'email (exemple)
    private boolean isValidEmail(String email) {
        // Vérifiez si l'e-mail correspond à un format valide à l'aide d'une expression régulière
        // Voici un exemple d'expression régulière simple pour valider les e-mails :
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Vérifiez si l'e-mail correspond au format spécifié par l'expression régulière
        if (email.matches(regex)) {
            // Si l'e-mail est valide, retournez true
            return true;
        } else {
            // Sinon, retournez false
            return false;
        }
    }


    private void sendReservationNotification(String serviceName, String userEmail) {
        // Composing the subject line for the email
        String subject = "New Reservation: " + serviceName;

        // Constructing the body of the email
        String body = "Dear Valued Customer,\n\nWe extend our sincerest gratitude for choosing our services. It is with great pleasure that we inform you of a recent reservation made for the service: "
                + serviceName + ".\n\nRest assured, your reservation \n \n\n***** is currently being diligently processed by our team ******** \n\n\n. We endeavor to provide you with a seamless experience and will notify you promptly once your reservation has been confirmed.\n\n"
                + "Should you require any assistance or have any inquiries regarding your reservation, please do not hesitate to reach out to us. Your satisfaction is our utmost priority.\n\n"
                + "Thank you for entrusting us with your reservation. We appreciate your patience and understanding.\n\nWarm Regards,\n[Your Company Name]";

        try {
            // Sending the email
            EmailSender.sendEmail(userEmail, subject, body);

            // Notifying the user and providing feedback
            System.out.println("Email sent successfully!");
            messageLabel.setText("Your reservation is currently being processed. An email notification has been sent to " + userEmail + ".");
        } catch (MessagingException e) {
            // Handling the case where email sending fails
            System.err.println("Failed to send email: " + e.getMessage());
            messageLabel.setText("Failed to send notification email. Please contact customer support for assistance.");
        }
    }


}




















