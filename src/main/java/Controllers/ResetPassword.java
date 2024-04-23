package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import models.User;
import services.UserService;

import java.security.MessageDigest;

public class ResetPassword {

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label MessageLabel;

    private User currentUser; // Keep track of the current user

    // Setter method to set the current user
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @FXML
    void resetPasswordButtonClicked(ActionEvent event) {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Check if passwords match
        if (newPassword.equals(confirmPassword)) {
            String encryptedPassword = encryptPassword(newPassword);
            // Update the user's password in the database
            UserService userService = new UserService();
            userService.updatePassword(currentUser.getEmail(), encryptedPassword);

            // Set the verification code to null for the user
            userService.clearVerificationCode(currentUser.getEmail());
            MessageLabel.setText("Password Reset Correctly!");

            // Display success message or redirect to login page
            // You can handle this based on your application flow
        } else {
            // Display error message for passwords not matching
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password Mismatch");
            alert.setHeaderText(null);
            alert.setContentText("The passwords do not match. Please try again.");
            alert.showAndWait();
        }
    }


    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
