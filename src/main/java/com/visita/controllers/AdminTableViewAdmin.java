package com.visita.controllers;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import com.visita.models.Admin;
import com.visita.models.User;
import com.visita.services.AdminService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminTableViewAdmin implements Initializable {
    @FXML
    private Button deleteButton;
    private Admin loggedInAdmin;
    @FXML
    private Button profileButton;

    @FXML
    private Label usernameLabel;
    @FXML
    private ImageView userImageView;
    @FXML
    private TableView<Admin> tableView;



    @FXML
    private TableColumn<Admin, String> usernameColumn;

    @FXML
    private TableColumn<Admin, String> fullnameColumn;

    @FXML
    private TableColumn<Admin, String> emailColumn;

    @FXML
    private TableColumn<Admin, String> phoneColumn;
    @FXML
    private TextField searchButton;
    @FXML
    private TableColumn<Admin, String> photoColumn;
    @FXML
    private TableColumn<Admin, Void> blockColumn;
    @FXML
    private TableColumn<Admin, Void> switchColumn;
    @FXML
    private TableColumn<Admin, Void> TrashColumn;
    private boolean isTableViewVisible = true;
    @FXML
    private Button pdfButton;



    private AdminService adminService = new AdminService();

    public void setLoggedInAdmin(Admin admin) {
        this.loggedInAdmin = admin;
        // After setting the patient, initialize the labels
        initializeLabels(admin);
    }

    public void initData(Admin admin, User loggedInUser) {
        // Check if the provided user is an instance of Patient
        if (admin != null && loggedInUser != null && loggedInUser instanceof Admin) {
            // If both the patient and loggedInUser are not null, cast loggedInUser to Patient
            // and initialize labels with patient data
            loggedInAdmin = (Admin) loggedInUser;

        } else {
            // Handle invalid data or show an error message
            showAlert("Error", "Invalid data provided");
        }
    }

    public AdminTableViewAdmin() {
        // Create PatientService with DataSource instance
        this.adminService = new AdminService();
    }
    // Method to initialize the view with patient data


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (loggedInAdmin != null) {
            initializeLabels(loggedInAdmin);
        }

        // Fetch the list of patients
        List<Admin> admins = adminService.afficher();

        // Create an ObservableList from the list of patients
        ObservableList<Admin> observableList = FXCollections.observableArrayList(admins);

        // Set the items of the TableView
        tableView.setItems(observableList);

        // Set cell value factories for each column

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        fullnameColumn.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        photoColumn.setCellValueFactory(new PropertyValueFactory<>("photo"));

        // Configure the photo column to display images
        photoColumn.setCellFactory(column -> {
            return new TableCell<Admin, String>() {
                private final ImageView imageView = new ImageView();

                {
                    imageView.setFitWidth(30);
                    imageView.setFitHeight(30);
                }

                @Override
                protected void updateItem(String photoUrl, boolean empty) {
                    super.updateItem(photoUrl, empty);
                    if (empty || photoUrl == null || photoUrl.isEmpty()) {
                        imageView.setImage(null);
                    } else {
                        Image image = new Image(photoUrl);
                        imageView.setImage(image);
                    }
                    setGraphic(imageView);
                }
            };
        });

        // Create the Block button column
        blockColumn = new TableColumn<>("Block");
        blockColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Admin, Void> call(TableColumn<Admin, Void> param) {
                return new BlockButtonCell(adminService);
            }
        });
        switchColumn = new TableColumn<>("Switch");
        switchColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Admin, Void> call(TableColumn<Admin, Void> param) {
                return new SwitchButtonCell(adminService);
            }
        });
        TrashColumn = new TableColumn<>("Delete");
        TrashColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Admin, Void> call(TableColumn<Admin, Void> param) {
                return new DeleteButtonCell(adminService);
            }
        });
        searchButton.textProperty().addListener((observable, oldValue, newValue) -> {
            // Call a method to update the table based on the new search value
            searchAdmins();
        });
        // Add the Block button column to the TableView
        tableView.getColumns().add(blockColumn);
        tableView.getColumns().add(switchColumn);
        tableView.getColumns().add(TrashColumn);
    }

    // Inner class for the Block button cell
    class BlockButtonCell extends TableCell<Admin, Void> {
        private final Button blockButton;
        private final AdminService adminService;

        public BlockButtonCell(AdminService adminService) {
            this.adminService = adminService;
            this.blockButton = new Button();
            // Create an ImageView for the ban icon
            ImageView banIcon = new ImageView(new Image(getClass().getResourceAsStream("/values/ban.png")));
            banIcon.setFitWidth(20); // Adjust the width as needed
            banIcon.setFitHeight(20);
            // Set the ban icon as the graphic for the button
            blockButton.setGraphic(banIcon);
            blockButton.setOnAction(event -> {
                Admin admin = getTableView().getItems().get(getIndex());
                System.out.println("Patient object: " + admin);
                // Call the block method of the patientService
                if (admin.getToken().equals("0")) {
                    // Unblock the patient
                    adminService.unblock(admin);
                } else {
                    // Block the patient
                    adminService.block(admin);
                }
                // Refresh the TableView to reflect the changes
                getTableView().refresh();
            });

        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                Admin admin = getTableView().getItems().get(getIndex());
                if (admin.getToken().equals("0")) {
                    // User is blocked, set button color to green
                    blockButton.setStyle("-fx-background-color: #0f730f;");
                } else {
                    // User is not blocked, set button color to red
                    blockButton.setStyle("-fx-background-color: #ad1111;");
                }
                setGraphic(blockButton);
            }
        }
    }

    class DeleteButtonCell extends TableCell<Admin, Void> {
        private final Button TrashButton;
        private final AdminService adminService;

        public DeleteButtonCell(AdminService adminService) {
            this.adminService = adminService;
            this.TrashButton = new Button();
            // Create an ImageView for the ban icon
            ImageView banIcon = new ImageView(new Image(getClass().getResourceAsStream("/values/trash.png")));
            banIcon.setFitWidth(20); // Adjust the width as needed
            banIcon.setFitHeight(20);

            // Set the ban icon as the graphic for the button
            TrashButton.setGraphic(banIcon);
            TrashButton.setOnAction(event -> {
                Admin admin = getTableView().getItems().get(getIndex());
                System.out.println("Patient object: " + admin);
                // Call the block method of the patientService

                // Unblock the patient
                adminService.supprimer(admin);

                // Block the patient

                // Refresh the TableView to reflect the changes
                getTableView().refresh();
            });

        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                TrashButton.setStyle("-fx-background-color: #0f736b;");
                setGraphic(null);
            } else {
                TrashButton.setStyle("-fx-background-color: #0f736b;");
                setGraphic(TrashButton);
            }
        }
    }

    class SwitchButtonCell extends TableCell<Admin, Void> {
        private final Button SwitchButton;
        private final AdminService adminService;

        public SwitchButtonCell(AdminService adminService) {
            this.adminService = adminService;
            this.SwitchButton = new Button();


            SwitchButton.setOnAction(event -> {
                Admin admin = getTableView().getItems().get(getIndex());
                System.out.println("Admin object: " + admin);
                // Call the block method of the patientService
                if (admin.getRole().equals("Admin")) {
                    // Unblock the patient
                    adminService.switchToAdmin(admin);

                } else {
                    // Block the patient
                    adminService.switchToUser(admin);
                    ObservableList<Admin> items = getTableView().getItems();
                    items.remove(admin);
                }
                // Refresh the TableView to reflect the changes
                getTableView().refresh();
            });

        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                Admin admin = getTableView().getItems().get(getIndex());
                if (admin.getRole().equals("admin")) {

                    SwitchButton.setText("Admin");
                    SwitchButton.setStyle("-fx-background-color: #0f736b;");
                } else {
                    SwitchButton.setText("Patient");
                    SwitchButton.setStyle("-fx-background-color: #bebb1c;");
                }
                setGraphic(SwitchButton);
            }
        }
    }


    @FXML
    void searchAdmins() {
        // Get the user's search criteria
        String searchText = searchButton.getText().toLowerCase();

        // Filter the list of patients based on the search criteria
        List<Admin> allPatients = adminService.afficher(); // Get all patients
        List<Admin> filteredPatients = new ArrayList<>();

        for (Admin admin : allPatients) {
            // Check if username, email, phone, or fullname of the patient matches the search criteria
            boolean matchesUsername = admin.getUsername().toLowerCase().contains(searchText);
            boolean matchesEmail = admin.getEmail().toLowerCase().contains(searchText);
            boolean matchesPhone = admin.getPhone().toLowerCase().contains(searchText);
            boolean matchesFullname = admin.getFullname().toLowerCase().contains(searchText);

            // If any of the fields match the search criteria, add the patient to the filtered list
            if (matchesUsername || matchesEmail || matchesPhone || matchesFullname) {
                filteredPatients.add(admin);
            }
        }

        // Update the TableView with the filtered patients
        ObservableList<Admin> observableList = FXCollections.observableList(filteredPatients);
        tableView.setItems(observableList);
    }



    @FXML
    private void handlePdfButtonAction(ActionEvent event) {
        // Generate PDF with table data
        generatePdf();
    }



    private void generatePdf() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("AdminTable.pdf"));
            document.open();

            PdfPTable pdfTable = new PdfPTable(tableView.getColumns().size() - 3); // Exclude switch and block columns

            // Add column headers to the PDF table, excluding switch and block columns
            for (TableColumn<?, ?> column : tableView.getColumns()) {
                if (!column.getText().equals("Switch") && !column.getText().equals("Block") && !column.getText().equals("Delete")) {
                    PdfPCell cell = new PdfPCell(new Phrase(column.getText()));
                    // Set background color for header cells
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY); // You can set any color here
                    pdfTable.addCell(cell);
                }
            }

            // Add table data to the PDF table, excluding switch and block columns
            ObservableList<Admin> patients = tableView.getItems();
            for (Admin patient : patients) {
                pdfTable.addCell(patient.getUsername());
                pdfTable.addCell(patient.getFullname());
                pdfTable.addCell(patient.getEmail());
                pdfTable.addCell(patient.getPhone());
                // Add photo here, assuming the photo column is the last column
                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(patient.getPhoto()); // Assuming getPhoto() returns the file path
                image.scaleToFit(40, 40); // Adjust width and height as needed
                PdfPCell photoCell = new PdfPCell(image, true); // true for fit to cell
                pdfTable.addCell(photoCell);
            }

            document.add(pdfTable);
            document.close();
            System.out.println("PDF file generated successfully.");

            // Toggle TableView visibility
            tableView.setVisible(isTableViewVisible);
            isTableViewVisible = !isTableViewVisible; // Toggle the flag for the next action

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }




    // Method to initialize labels with patient data
    private void initializeLabels(Admin admin) {
        try {
            // Populate labels with patient data
            usernameLabel.setText(admin.getUsername());

            String imagePath = admin.getPhoto(); // Replace with the actual method to get the image path
            if (imagePath != null && !imagePath.isEmpty()) {
                Image image = new Image(imagePath);
                userImageView.setImage(image);
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during initialization
            showAlert("Error", "An error occurred during initialization: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteButton() {
        if (loggedInAdmin == null) {
            // Handle the case where loggedInPatient is null
            showAlert("Error", "Logged in patient is null.");
            return;
        }

        // Create an alert to confirm the deletion
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Account");
        alert.setHeaderText("Are you sure you want to delete your account?");
        alert.setContentText("This action cannot be undone.");

        // Show the alert and wait for user response
        Optional<ButtonType> result = alert.showAndWait();

        // If the user confirms deletion, delete the patient account
        result.ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    // Call the supprimer method from the PatientService class to delete the account
                    AdminService adminService = new AdminService();
                    adminService.supprimer(loggedInAdmin);

                    // Close the current window after successful deletion
                    Stage stage = (Stage) deleteButton.getScene().getWindow();
                    stage.close();

                    // Redirect to the signup page
                    redirectToSignupPage();
                } catch (Exception e) {
                    // Handle any exceptions that occur during deletion
                    showAlert("Error", "An error occurred while deleting the patient account: " + e.getMessage());
                    e.printStackTrace(); // Log the exception
                }
            }
        });
    }

    private void redirectToSignupPage() {
        try {
            // Load the Signup.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Signup.fxml"));
            Parent root = loader.load();

            // Create a new stage for the signup window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            // Handle any IOException that might occur during loading
            showAlert("Error", "An error occurred while loading the signup page: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Helper method to display an alert message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }



    // Setter method for the loggedInPatient variable


    @FXML
    private void RedirectToModifyAdmin(ActionEvent event) {
        redirectToUpdateUserController(event, loggedInAdmin);
    }

    private void redirectToUpdateUserController(ActionEvent event, User user) {
        try {
            Window window = ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateAdmin.fxml"));
            Parent root = loader.load();

            // Pass the authenticated user to the controller
            UpdateAdmin updateAdminController = loader.getController();
            updateAdminController.setLoggedInAdmin((Admin) user);
            Stage stage = (Stage) window;

            // Set the new scene
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogoutButton(ActionEvent event) {
        // Code to handle the logout action goes here
        logout(event);
    }

    private void logout(ActionEvent event) {
        loggedInAdmin = null;

        // Redirect the user to the login page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void showDoctorAction(ActionEvent event)  throws IOException{
        MenuItem menuItem = (MenuItem) event.getSource();
        Parent parent = (Parent) menuItem.getParentPopup().getOwnerNode();
        Scene scene = parent.getScene();
        Window window = scene.getWindow();
        RedirectToTableViewAdminDoctor(event, loggedInAdmin, window);
    }


    private void RedirectToTableViewAdminDoctor(ActionEvent event, User user,Window window) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminTableviewDoctor.fxml"));
        Parent root = loader.load();
        AdminTableViewDoctor AdminTableViewDoctorController = loader.getController();
        AdminTableViewDoctorController.setLoggedInAdmin((Admin) user);
        Stage stage = (Stage) window;
        stage.setScene(new Scene(root));
    }
    @FXML
    private void RedirectToProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAdmin.fxml"));
            Parent root = loader.load();

            // Pass the modified patient object to the AfficherUser controller
            AfficherAdmin afficherAdminController = loader.getController();
            afficherAdminController.setLoggedInAdmin(loggedInAdmin);

            // Get the current stage
            Stage stage = (Stage) profileButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showUserAction(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();
        Parent parent = (Parent) menuItem.getParentPopup().getOwnerNode();
        Scene scene = parent.getScene();
        Window window = scene.getWindow();
        RedirectToTableViewAdminUser(event, loggedInAdmin, window);
    }


    private void RedirectToTableViewAdminUser(ActionEvent event, User user, Window window) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminTableview.fxml"));
        Parent root = loader.load();
        AdminTableView AdminTableViewController = loader.getController();
        AdminTableViewController.setLoggedInAdmin((Admin) user);
        Stage stage = (Stage) window;
        stage.setScene(new Scene(root));
    }

}

