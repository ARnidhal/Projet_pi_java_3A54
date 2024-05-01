package com.visita.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.visita.models.Patient;
import com.visita.models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import com.visita.services.PatientService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Chatbot implements Initializable {
    @FXML
    private TextField questionField;

    @FXML
    private TextArea responseField;
    private Patient loggedInPatient;
    @FXML
    private Button updateButton;
    private PatientService patientService = new PatientService();

    public Chatbot() {
        // Create PatientService with DataSource instance
        this.patientService = new PatientService();
    }

    public void initData(User user) {
        // Check if the provided user is an instance of Patient
        if (user instanceof Patient) {
            // If the user is a Patient, cast it to Patient and initialize labels
            Patient patient = (Patient) user;

        } else {
            // Handle other types of users or show an error message
            showAlert("Error", "Invalid user type");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Check if the loggedInPatient object is not null
        if (loggedInPatient != null) {
            // Initialize the view with patient data

        } else {
            // Display an error message if the loggedInPatient object is null

        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean isHealthQuestion(String question) {
        // Define health-related keywords
        String[] healthKeywords = {
                "health", "medical", "doctor", "hospital", "wellness", "fitness", "nutrition",
                "disease", "illness", "symptoms", "treatment", "therapy", "diagnosis", "medicine",
                "vaccine", "infection", "exercise", "diet", "mental health", "physical health",
                "public health", "healthy lifestyle", "healthcare", "pharmacy", "immunity", "pandemic",
                "emergency", "surgery", "prescription", "checkup", "virus", "bacteria", "contagious",
                "recovery", "pain", "stress", "chronic", "acute", "blood pressure", "heart rate",
                "respiratory", "immune system", "cancer", "stroke", "diabetes", "allergy", "asthma",
                "obesity", "nutrition", "sleep", "dietary", "mental illness", "addiction", "vaccination",
                "pandemic", "epidemic", "quarantine", "pandemic", "outbreak", "coronavirus", "COVID-19",
                // Add more health-related keywords here
                "hospice", "hypertension", "hormones", "anxiety", "depression", "alcoholism", "smoking",
                "tobacco", "sugar intake", "sugar levels", "exercise routine", "workout plan", "mental wellness",
                "physical therapy", "occupational therapy", "cardiovascular health", "blood sugar", "glucose levels",
                "immune response", "viral infection", "bacterial infection", "cognitive health", "brain health",
                "neurological disorders", "digestive health", "gastrointestinal issues", "hydration", "water intake",
                "health screening", "preventive care", "medical history", "family medical history", "chronic condition",
                "acute condition", "acute care", "chronic care", "chronic disease", "respiratory infection",
                "respiratory illness", "heart disease", "heart condition", "lung health", "kidney health",
                "liver health", "bone health", "osteoporosis", "arthritis", "joint pain", "muscle pain",
                "menstrual health", "menstrual cycle", "menopause", "reproductive health", "sexual health",
                "sexual wellness", "pregnancy", "fertility", "contraception", "birth control", "STDs", "STIs",
                "sexually transmitted diseases", "sexually transmitted infections", "mental wellbeing",
                "emotional health", "stress management", "mental resilience", "emotional resilience",
                "substance abuse", "drug addiction", "drug abuse", "alcohol addiction", "alcohol abuse",
                "nutritional supplements", "vitamins", "minerals", "nutrient intake", "dietary supplements"
        };

        // Check if any of the keywords appear in the question
        for (String keyword : healthKeywords) {
            if (question.toLowerCase().contains(keyword.toLowerCase())) {
                return true;
            }
        }

        return false;
    }



    @FXML
    void submitQuestion(ActionEvent event) {
        // Get the question entered by the user
        String question = questionField.getText().trim();

        // Check if the question is related to health
        if (isHealthQuestion(question)) {
            // If it's a health-related question, proceed to get the chatbot response
            String response = getChatbotResponse(question);

            // Clear the response field before typing the response
            responseField.clear();

            // Simulate typing effect by gradually updating the response field
            Timeline timeline = new Timeline();
            for (int i = 0; i < response.length(); i++) {
                final int index = i;
                KeyFrame keyFrame = new KeyFrame(Duration.millis(i * 50), event1 -> {
                    responseField.appendText(String.valueOf(response.charAt(index)));
                });
                timeline.getKeyFrames().add(keyFrame);
            }
            timeline.play();
        } else {
            // If it's not a health-related question, display an error message or handle it accordingly
            responseField.setText("Error: Please enter a question related to health.");
        }
    }


    private String getChatbotResponse(String question) {
        String apiKey = "sk-proj-UioNvhzJXDCgvrJXuJhoT3BlbkFJzwPbrxcr6dHo83DCm988"; // Replace with your OpenAI API key
        String apiUrl = "https://api.openai.com/v1/completions";
        try {
            // Prepare the request body
            String requestBody = "{\"prompt\": \"" + question + "\", \"max_tokens\": 150, \"model\": \"gpt-3.5-turbo-instruct\"}";

            // Create HttpURLConnection
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setDoOutput(true);

            // Write request body
            conn.getOutputStream().write(requestBody.getBytes(StandardCharsets.UTF_8));

            // Check HTTP response code
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                Scanner scanner = new Scanner(conn.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNextLine()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();

                // Extract response
                return extractResponse(response.toString());
            } else {
                // Read error response
                Scanner errorScanner = new Scanner(conn.getErrorStream());
                StringBuilder errorResponse = new StringBuilder();
                while (errorScanner.hasNextLine()) {
                    errorResponse.append(errorScanner.nextLine());
                }
                errorScanner.close();

                // Print error response
                System.out.println("Error response from server: " + errorResponse.toString());

                return "Error: Failed to get response from the chatbot. Status code: " + responseCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: Failed to communicate with the chatbot API";
        }
    }

    private String extractResponse(String responseBody) {
        try {
            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(responseBody);

            // Check if the response contains a 'choices' array
            if (jsonResponse.has("choices")) {
                JSONArray choicesArray = jsonResponse.getJSONArray("choices");

                // Check if the 'choices' array is not empty
                if (choicesArray.length() > 0) {
                    // Get the first choice (assuming it's the response)
                    JSONObject firstChoice = choicesArray.getJSONObject(0);

                    // Extract the text from the choice
                    if (firstChoice.has("text")) {
                        return firstChoice.getString("text");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return an error message if the response format is unexpected
        return "Error: Unable to extract response from API";
    }

    public void setLoggedInPatient(Patient patient) {
        this.loggedInPatient = patient;
        // After setting the patient, initialize the labels

    }
    @FXML
    private void RedirectToProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Afficheruser.fxml"));
            Parent root = loader.load();

            // Pass the modified patient object to the AfficherUser controller
            AfficherUser afficherUserController = loader.getController();
            afficherUserController.setLoggedInPatient(loggedInPatient);

            // Get the current stage
            Stage stage = (Stage) updateButton.getScene().getWindow();

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
        loggedInPatient = null;

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
}