package com.visita.Controllers;
import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPCell;
import javafx.stage.FileChooser;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.File;
import javafx.stage.FileChooser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.visita.Controllers.Chartreservation;
import com.visita.models.ReservationService;
import com.visita.models.Service;
import com.visita.services.Categoryservice;
import com.visita.services.ReservationSrvService;
import com.visita.models.Category;
import com.visita.services.ServiceService;
import jakarta.mail.MessagingException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.util.*;
import java.util.List;

import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Backresevservice {

    @FXML
    private TableView<ReservationService> TableView;
    @FXML
    private ListView<String> list_service;
    @FXML
    private TextField afficherreserv_search;

    @FXML
    private Button afficherresv_clearBtn;

    @FXML
    private Button afficherresv_confBtn;

    @FXML
    private Button afficherresv_rejectBtn;

    @FXML
    private AnchorPane afficherserv_form;

    @FXML
    private TableColumn<ReservationService, String> affresv_col_EMAIL;

    @FXML
    private TableColumn<ReservationService, String> affresv_col_NAME;

    @FXML
    private TableColumn<ReservationService, String> affresv_col_servname;

    @FXML
    private TextField affsrv_Email;

    @FXML
    private TextField affsrv_NAME;

    @FXML
    private TextField affsrv_Service;

    @FXML
    private AnchorPane main_form;
    @FXML
    private Label verf;
    @FXML
    private Button charts;
    @FXML
    private Button export_EXL;
    @FXML
    private Button pdf_btn;
    private boolean isTableViewVisible = true;
    @FXML
    public  void close(){
        System.exit(0);
    }
    ReservationSrvService ps= new  ReservationSrvService();
    @FXML
    public void minimize(){

        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private BarChart<String, Number> chartreservationservice;


    @FXML
    void clearFields(ActionEvent event) {
        // Effacer les champs de texte
        affsrv_NAME.setText("");
        affsrv_Email.setText("");
        affsrv_Service.setText("");
    }
    void clearFields() {
        affsrv_NAME.setText("");
        affsrv_Email.setText("");
        affsrv_Service.setText("");

    }

    private void refreshTableView() {
        List<ReservationService> reservationServices = ps.afficher();
        ObservableList<ReservationService> observableList = FXCollections.observableList(reservationServices);
        TableView.setItems(observableList);
        // Force the TableView to refresh
        TableView.refresh();
    }
    @FXML
    public void initialize() {
        ReservationSrvService reservationsrvservice = new ReservationSrvService();

        // Configurer la TableView pour permettre la sélection multiple
        TableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Configurer l'écouteur de changement de sélection pour la TableView
        TableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Vérifier si un nouveau service est sélectionné
            if (newValue != null) {
                // Remplir les champs de texte avec les propriétés du service sélectionné
                affsrv_NAME.setText(newValue.getNom());
                affsrv_Email.setText(newValue.getEmail());
                affsrv_Service.setText(newValue.getService_nom());
               /* // Définir la catégorie sélectionnée
                Category selectedCategory = findCategoryByName(newValue.getCategory_nom());
                addService_CATEGORY.setValue(selectedCategory);*/
            } else {
                // Effacer les champs de texte si aucun service n'est sélectionné
                clearFields();
            }
        });



        // Configurez les colonnes de tableView

        affresv_col_NAME.setCellValueFactory(new PropertyValueFactory<ReservationService, String>("nom"));
        affresv_col_EMAIL.setCellValueFactory(new PropertyValueFactory<ReservationService, String>("email"));
        affresv_col_servname.setCellValueFactory(new PropertyValueFactory<ReservationService, String>("service_nom"));
        // Afficher les statistiques de réservation
        //handleShowStatisticsButtonClick();
        // Chargez les services lorsque le contrôleur est initialisé
        refreshTableView();
    }

    @FXML
    void supprimerReservserv(ActionEvent event) {
        // Obtenir la catégorie sélectionnée dans la TableView
        ReservationService selectedReservation = TableView.getSelectionModel().getSelectedItem();

        // Vérifier si une cReservation est sélectionnée
        if (selectedReservation != null) {
            // Demander confirmation à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmer la suppression");
            alert.setHeaderText("Voulez-vous vraiment supprimer cette Reservation ?");
            alert.setContentText("Service Reserverver : " + selectedReservation.getService_nom() + "\n\nCette action est irréversible. Veuillez confirmer.");

            Optional<ButtonType> result = alert.showAndWait();

            // Si l'utilisateur confirme, supprimer la catégorie
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer la catégorie avec le service
                ps.supprimer(selectedReservation);
                refreshTableView();

                // Afficher un message de succès
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Suppression de la RESERVATION");
                successAlert.setHeaderText("RESERVATION REJECTER !");
                successAlert.setContentText("La RESERVATION  \"" + selectedReservation.getNom() + "\" a été supprimée avec succès.\n\n Opération réussie !");
                successAlert.showAndWait();
            }
        } else {
            // Aucune catégorie sélectionnée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sélection manquante");
            alert.setHeaderText("Erreur de sélection");
            alert.setContentText(" Il semble que vous n'ayez pas sélectionné de Reservation. Veuillez en choisir une à supprimer.");
            alert.showAndWait();
        }
        // Effacer les champs de texte après la modification
        clearFields();
        // Send email notification
        sendReservationNotificationrej(selectedReservation.getNom(), selectedReservation.getEmail(),false);
    }


    @FXML
    void ConfirmerReservserv(ActionEvent event) {
        // Obtenir la catégorie sélectionnée dans la TableView
        ReservationService selectedReservation = TableView.getSelectionModel().getSelectedItem();

        // Vérifier si une cReservation est sélectionnée
        if (selectedReservation != null) {
            // Demander confirmation à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmer la RESERVATION ");
            alert.setHeaderText("Voulez-vous vraiment confirmer cette Reservation ?");
            alert.setContentText("Service Reserverver : " + selectedReservation.getService_nom() + "\n\nCette action est irréversible. Veuillez confirmer.");

            Optional<ButtonType> result = alert.showAndWait();

            // Si l'utilisateur confirme, supprimer la catégorie
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer la catégorie avec le service
                ps.supprimer(selectedReservation);
                refreshTableView();

                // Afficher un message de succès
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("RESERVATION réussie ");
                successAlert.setHeaderText(" LA RESERVATION EST  CONFIRMER  !");
                successAlert.setContentText("LA RESERVATION \"" + selectedReservation.getNom() + "\" a été supprimée avec succès.\n\n Opération réussie !");
                successAlert.showAndWait();
            }
        } else {
            // Aucune catégorie sélectionnée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sélection manquante");
            alert.setHeaderText("Erreur de sélection");
            alert.setContentText(" Il semble que vous n'ayez pas sélectionné de Reservation. Veuillez en choisir une à supprimer.");
            alert.showAndWait();
        }
        // Effacer les champs de texte après la modification
        clearFields();

        // Send email notification
        sendReservationNotificationconf(selectedReservation.getNom(), selectedReservation.getEmail(),true);

    }







    @FXML
    void searchReservationServ() {
        // Obtenez les critères de recherche de l'utilisateur
        String searchText = afficherreserv_search.getText().toLowerCase();

        // Filtrer la liste des catégories en fonction des critères de recherche
        List<ReservationService> reservationServices = ps.afficher(); // Récupérez toutes les catégories
        List<ReservationService> filteredreservationServices= new ArrayList<>();

        for (ReservationService reservationservice : reservationServices) {
            // Vérifiez si l'ID, le nom, la description ou l'icône de la catégorie correspondent aux critères de recherche

            boolean matchesName = reservationservice.getNom().toLowerCase().contains(searchText);
            boolean matchesEmail = reservationservice.getEmail().toLowerCase().contains(searchText);
            boolean matchesService = reservationservice.getService_nom().toLowerCase().contains(searchText);

            // Si l'un des champs correspond aux critères de recherche, ajoutez la catégorie à la liste filtrée
            if ( matchesName || matchesEmail || matchesService) {
                filteredreservationServices.add(reservationservice);
            }
        }

        // Mettre à jour la TableView avec les catégories filtrées
        ObservableList<ReservationService> observableList = FXCollections.observableList(filteredreservationServices);
        TableView.setItems(observableList);
    }


    private void sendReservationNotificationconf(String serviceName, String userEmail, boolean isSuccessful) {
        // Composing the subject line for the email
        String subject = isSuccessful ? "New Reservation: " + serviceName : "Reservation Rejection: " + serviceName;

        // Constructing the body of the email
        String processingMessage = " \n\n *****currently being diligently processed by our team ***** \n\n";
        String confirmationMessage = " \n\n ***** has been successfully confirmed ***** \n\n";
        String rejectionMessage = " \n\n *****has been rejected ***** \n\n";

        String statusMessage = isSuccessful ? processingMessage : rejectionMessage;
        if (isSuccessful) {
            statusMessage = confirmationMessage;
        }

        String body = "Dear Valued Customer,\n\nWe extend our sincerest gratitude for choosing our services. It is with great pleasure that we inform you of a recent reservation made for the service: "
                + serviceName + ".\n\nRest assured, your reservation " + confirmationMessage + ". We endeavor to provide you with a seamless experience and will notify you promptly once your reservation has been confirmed.\n\n"
                + "Should you require any assistance or have any inquiries regarding your reservation, please do not hesitate to reach out to us. Your satisfaction is our utmost priority.\n\n"
                + "Thank you for entrusting us with your reservation. We appreciate your patience and understanding.\n\nWarm Regards,\n[VISITA]";

        try {
            // Sending the email
            EmailSender.sendEmail(userEmail, subject, body);

            // Notifying the user and providing feedback
            System.out.println("Email sent successfully!");
            verf.setText(isSuccessful ? "The reservation has been confirmed. An email notification has been sent to " + userEmail + "." : "Your reservation has been rejected. An email notification has been sent to " + userEmail + ".");
        } catch (MessagingException e) {
            // Handling the case where email sending fails
            System.err.println("Failed to send email: " + e.getMessage());
            verf.setText("Failed to send notification email. Please contact customer support for assistance.");
        }
    }

    private void sendReservationNotificationrej(String serviceName, String userEmail, boolean isSuccessful) {
        // Composing the subject line for the email
        String subject = isSuccessful ? "New Reservation: " + serviceName : "Reservation Rejection: " + serviceName;

        // Constructing the body of the email
        String processingMessage = " \n\n *****currently being diligently processed by our team ***** \n\n";
        String confirmationMessage = " \n\n ***** has been successfully confirmed ***** \n\n";
        String rejectionMessage = " \n\n *****has been rejected ***** \n\n";

        String statusMessage = isSuccessful ? processingMessage : rejectionMessage;
        if (isSuccessful) {
            statusMessage = confirmationMessage;
        }

        String body = "Dear Valued Customer,\n\nWe extend our sincerest gratitude for choosing our services. It is with great pleasure that we inform you of a recent reservation made for the service: "
                + serviceName + ".\n\nRest assured, your reservation " +  rejectionMessage + ". We endeavor to provide you with a seamless experience and will notify you promptly once your reservation has been confirmed.\n\n"
                + "Should you require any assistance or have any inquiries regarding your reservation, please do not hesitate to reach out to us. Your satisfaction is our utmost priority.\n\n"
                + "Thank you for entrusting us with your reservation. We appreciate your patience and understanding.\n\nWarm Regards,\n[VISITA]";

        try {
            // Sending the email
            EmailSender.sendEmail(userEmail, subject, body);

            // Notifying the user and providing feedback
            System.out.println("Email sent successfully!");
            verf.setText(isSuccessful ? "The reservation has been REJECTED . An email notification has been sent to " + userEmail + "." : "THE reservation has been rejected. An email notification has been sent to " + userEmail + ".");
        } catch (MessagingException e) {
            // Handling the case where email sending fails
            System.err.println("Failed to send email: " + e.getMessage());
            verf.setText("Failed to send notification email. Please contact customer support for assistance.");
        }
    }


    @FXML
    private void handleShowChartButtonClick(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface Chartreservation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/chartreservation.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur Chartreservation
            Chartreservation chartController = loader.getController();

            // Appeler la méthode showReservationStatistics pour mettre à jour le bar chart
            chartController.showReservationStatistics();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre pour afficher l'interface Chartreservation
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePdfButtonAction(ActionEvent event) {
        Document document = new Document(PageSize.A4);

        try {
            // Specify the output path of the PDF file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("reservations.pdf");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
            File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

            if (selectedFile != null) {
                PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
                document.open();

                // Add a customized title
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
                Paragraph title = new Paragraph("Rapport de Réservations de Services", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20); // Add spacing after the title
                document.add(title);

                // Add a logo
                Image logo = Image.getInstance("C:\\Users\\user\\IdeaProjects\\visita\\src\\main\\resources\\values\\default_image12.png");
                logo.setAlignment(Element.ALIGN_CENTER);
                logo.scaleToFit(200, 200);
                document.add(logo);

                // Create a table PDF with 3 columns for reservation data
                PdfPTable pdfTable = new PdfPTable(3);
                pdfTable.setWidthPercentage(100); // Set table width to 100% of page width

                // Add table headers with styling
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
                PdfPCell cell1 = new PdfPCell(new Phrase("Nom", headerFont));
                PdfPCell cell2 = new PdfPCell(new Phrase("Email", headerFont));
                PdfPCell cell3 = new PdfPCell(new Phrase("Service", headerFont));

                // Set background color for header cells
                cell1.setBackgroundColor(BaseColor.GRAY);
                cell2.setBackgroundColor(BaseColor.GRAY);
                cell3.setBackgroundColor(BaseColor.GRAY);

                pdfTable.addCell(cell1);
                pdfTable.addCell(cell2);
                pdfTable.addCell(cell3);

                // Get data from the TableView
                ObservableList             <ReservationService> reservationServices = TableView.getItems();

                // Add data from the TableView to the PDF table
                for (ReservationService reservationService : reservationServices) {
                    pdfTable.addCell(new Phrase(reservationService.getNom()));
                    pdfTable.addCell(new Phrase(reservationService.getEmail()));
                    pdfTable.addCell(new Phrase(reservationService.getService_nom()));
                }

                // Add the PDF table to the document
                document.add(pdfTable);

                // Close the document
                document.close();

                // Show a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("PDF Généré");
                alert.setHeaderText(null);
                alert.setContentText("Le fichier PDF a été généré avec succès !");
                alert.showAndWait();
            }
        } catch (DocumentException | IOException e) {
            // Handle exceptions
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de la génération du PDF !");
            alert.showAndWait();
        }
    }


    @FXML
    void exportToExcel() {
        // Utilisez FileChooser pour permettre à l'utilisateur de choisir l'emplacement du fichier Excel
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export to Excel");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(new Stage());

        // Si un fichier est sélectionné
        if (file != null) {
            try (Workbook workbook = new XSSFWorkbook()) {
                // Créez une feuille de calcul dans le classeur
                Sheet sheet = workbook.createSheet("Reservation Services");

                // Créez une ligne d'en-tête dans la feuille
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Nom");
                headerRow.createCell(1).setCellValue("EMAIL");
                headerRow.createCell(2).setCellValue("SERVICE");


                // Récupérez les services de votre TableView
                List<ReservationService> servicesList = new ArrayList<>(TableView.getItems());

                // Remplissez la feuille de calcul avec les données des services
                for (int i = 0; i < servicesList.size(); i++) {
                    Row row = sheet.createRow(i + 1);
                    ReservationService service = servicesList.get(i);

                    // Remplissez les cellules avec les données du service
                    row.createCell(0).setCellValue(service.getNom());
                    row.createCell(1).setCellValue(service.getEmail());
                    row.createCell(2).setCellValue(service.getService_nom());
                }

                // Écrire le classeur dans le fichier
                try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    workbook.write(fileOutputStream);
                }

                // Afficher un message de succès
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Exportation réussie");
                successAlert.setHeaderText("Exportation Excel");
                successAlert.setContentText("Les données ont été exportées avec succès !");
                successAlert.showAndWait();
            } catch (IOException e) {
                // Gérer les exceptions d'E/S
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur d'exportation");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de l'exportation des données.");
                alert.showAndWait();
            }
        }
    }





}






