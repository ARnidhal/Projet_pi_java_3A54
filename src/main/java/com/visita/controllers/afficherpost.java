package com.visita.controllers;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.visita.models.Patient;
import com.visita.models.post;
import com.visita.models.comment;
import com.visita.models.Report;
import com.visita.services.PatientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import com.visita.services.servicePost;
import com.visita.services.serviceComment;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class afficherpost {



    private int currentPage = 1; // Initialize current page to 1
    private int pageSize = 10; // Set page size to 10 posts per page
    private int totalPosts; // Total number of posts
    private int totalPages; // Total number of pages

    @FXML
    private VBox postContainer; // Container for displaying posts

    @FXML
    private ChoiceBox<String> choiceres;

    @FXML
    private TextField searchBar;
    @FXML
    private BorderPane parent;



    @FXML
    private Button previousPageButton;

    @FXML
    private Button nextPageButton;

    @FXML
    private Label pageInfoLabel;

    @FXML
    private DatePicker startDatePicker; // DatePicker for start date

    @FXML
    private DatePicker endDatePicker; // DatePicker for end date

    @FXML
    private TextField userField; // TextField for user filter

    @FXML
    private TextField locationField; // TextField for location filter

    @FXML
    private ChoiceBox<String> sortOptions; // ChoiceBox for sorting options

    @FXML
    private ToggleButton changemodebtn;


    private final servicePost sp = new servicePost();
    private final serviceComment sc = new serviceComment();


    private int test=0;

    @FXML
    private ToggleButton modeToggleButton;
    @FXML
    private ImageView img_mode;
    @FXML
    private TextArea commentInput;
    private Patient loggedInPatient;

    private PatientService patientService = new PatientService();
    @FXML
    private Label commentErrorLabel;

    private static final String ACCOUNT_SID = "AC99d1ef0629c601c75a25f4392a94f86e";
    private static final String AUTH_TOKEN = "3f10f31a4d8287bd4738d95410669731";
    private static final String TWILIO_PHONE_NUMBER = "+14582904104";


    public afficherpost() {
        // Create PatientService with DataSource instance
        this.patientService = new PatientService();
    }

    public void setLoggedInPatient(Patient patient) {
        this.loggedInPatient = patient;
        // After setting the patient, initialize the labels

    }
    private boolean isLightMode = true;

    public void changemode(ActionEvent event){
        isLightMode= !isLightMode;
        if (isLightMode){
            setLightMode();
        }else {
            setDarkMode();
        }
    }


    private void setLightMode() {
        parent.getStylesheets().remove("dark.css");
        parent.getStylesheets().add("style.css");

        try {
            Image image = new Image("file:/C:/Users/rayen/IdeaProjects/startfromthebottom/secondtryjavapidev/src/main/resources/values/icons8-moon-100.png");
            img_mode.setImage(image);
        } catch (IllegalArgumentException e) {
            System.err.println("Image not found: " + e.getMessage());
            // Handle error: set a default image or show an error message
        }
    }

    private void setDarkMode() {
        parent.getStylesheets().remove("style.css");
        parent.getStylesheets().add("dark.css");

        try {
            Image image = new Image("file:/C:/Users/rayen/IdeaProjects/startfromthebottom/secondtryjavapidev/src/main/resources/values/icons8-sun-100.png");
            img_mode.setImage(image);
        } catch (IllegalArgumentException e) {
            System.err.println("Image not found: " + e.getMessage());
            // Handle error: set a default image or show an error message
        }
    }








    public void initialize() {

        modeToggleButton.getStyleClass().add("toggle-switch");






        if (test==0) {
            // Initialize sorting options
            sortOptions.getItems().addAll();
        test=1;
        }
            sortOptions.setValue("Date"); // Set default sorting option
        // Add event handler to search bar and other components
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> searchPosts());
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> searchPosts());
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> searchPosts());
        userField.textProperty().addListener((observable, oldValue, newValue) -> searchPosts());
        locationField.textProperty().addListener((observable, oldValue, newValue) -> searchPosts());
        sortOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> searchPosts());
        // Calculate total number of posts
        totalPosts = sp.countTotalPosts();

        // Update total pages
        updateTotalPages();

        // Example: Load posts for the current page
        loadPosts(currentPage);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> searchPosts());

        commentInput.textProperty().addListener((observable, oldValue, newValue) -> validateCommentInput(newValue));

    }








    @FXML
    private void searchPosts() {
        // Get the search keyword
        String keyword = searchBar.getText() != null ? searchBar.getText().toLowerCase() : "";
        System.out.println(loggedInPatient.getId());

        // Get the start date and end date
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        // Initialize a date formatter to format LocalDate to String
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);

        // Convert LocalDate to String
        String startDateStr = startDate != null ? startDate.format(dateFormatter) : null;
        String endDateStr = endDate != null ? endDate.format(dateFormatter) : null;

        // Get the user and location filters
        String user = userField.getText() != null ? userField.getText().toLowerCase() : "";
        String location = locationField.getText() != null ? locationField.getText() : "";

        // Get the sort option
        String sortBy = sortOptions.getValue();

        // If no sort option is selected, you can provide a default
        if (sortBy == null) {
            sortBy = "date";  // Default to sorting by date if no other option is selected
        }

        // Call the search and filter method and get the filtered posts
        List<post> filteredPosts = sp.searchAndFilterPosts(keyword, startDateStr, endDateStr, user, location, sortBy);

        // Display the filtered posts
        displayPosts(filteredPosts);
    }

    @FXML
    private void loadPosts(int page) {
        // Calculate offset based on page number and page size
        int offset = (page - 1) * pageSize;
        // Load validated posts for the specified page
        List<post> posts = sp.getValidatedPostsForPage(offset, pageSize);
        // Display the loaded posts
        displayPosts(posts);
    }


    @FXML
    private void goToPreviousPage() {
        System.out.println("Previous Page Button Clicked");
        if (currentPage > 1) {
            currentPage--;
            loadPosts(currentPage);
            updateTotalPages(); // Update total pages
            updatePaginationUI(); // Update pagination UI

        }
        postContainer.getChildren().clear();
        initialize();
    }

    @FXML
    private void goToNextPage() {
        System.out.println("Next Page Button Clicked");
        if (currentPage < totalPages) {
            currentPage++;
            loadPosts(currentPage);
            updateTotalPages(); // Update total pages
            updatePaginationUI(); // Update pagination UI

        }
        postContainer.getChildren().clear();
        initialize();
    }



    @FXML
    private void updatePaginationUI() {
        // Update page information label
        pageInfoLabel.setText("Page " + currentPage + " of " + totalPages);

        // Disable previous page button if on the first page
        previousPageButton.setDisable(currentPage == 1);

        // Disable next page button if on the last page
        nextPageButton.setDisable(currentPage == totalPages);
    }


    private void updateTotalPages() {
        // Recalculate total number of pages
        totalPages = (int) Math.ceil((double) totalPosts / pageSize);
    }




    private void displayPosts(List<post> posts) {

        System.out.println(sp.countLikesForPost(77));

        postContainer.getChildren().clear();
        // Iterate through the list of posts and display each one
        for (post p : posts) {
            // Create labels for title, type, and description
            Label titleLabel = new Label("Title: " + p.getTitle_post());
            titleLabel.getStyleClass().add("title-label");
            Label typeLabel = new Label("Type: " + p.getType_post());
            typeLabel.getStyleClass().add("type-label");
            Label descriptionLabel = new Label("Description: " + p.getContenu_post());
            descriptionLabel.getStyleClass().add("description-label");

            // Create additional labels for date posted and country
            Label dateLabel = new Label("Date: " + p.getMakedate_post());
            dateLabel.getStyleClass().add("date-label");
            Label countryLabel = new Label("Country: " + p.getCountry());
            countryLabel.getStyleClass().add("country-label");

            Label likesLabel = new Label("Likes: " + sp.countLikesForPost(p.getId()));
            likesLabel.getStyleClass().add("likes-label");

            // Create an ImageView for displaying the image
            ImageView imageView = new ImageView(new Image(p.getImage_post()));
            imageView.setFitWidth(200); // Set width to 200 (adjust as needed)
            imageView.setFitHeight(200); // Set height to 200 (adjust as needed)

            // Create a BorderPane to act as the card
            BorderPane card = new BorderPane();
            card.getStyleClass().add("post-card");

            // Set left, top, and right contents
            VBox leftContainer = new VBox(titleLabel, typeLabel, dateLabel, countryLabel);
            card.setLeft(leftContainer);
            card.setTop(typeLabel);
            card.setRight(new StackPane(imageView));

            // Set bottom content
            VBox bottomContainer = new VBox(descriptionLabel, likesLabel);
            card.setBottom(bottomContainer);
            //BorderPane.setAlignment(likesLabel, Pos.BOTTOM_RIGHT);

            // Add event handler to show post details when clicked
            card.setOnMouseClicked(event -> showPostDetails(p));

            // Add the card to the postContainer
            postContainer.getChildren().add(card);


            // Optionally, set preferred width and margin for the card
            card.setPrefWidth(300); // Adjust the card width as needed
            card.setStyle("-fx-margin-left: 10; -fx-margin-right: 10;"); // Adjust left and right margin as needed
        }
    }

    // Inside afficherpost controller

    private void updateLikeButtonState(Button likeButton, int postId) {
        if (sp.hasLikedPost(postId, loggedInPatient.getId())) {
            //likeButton.setText("Unlike");
            Image image = new Image("file:/C:/Users/rayen/IdeaProjects/startfromthebottom/secondtryjavapidev/src/main/resources/values/icons8-love-96.png");

            // Create an ImageView from the image
            ImageView imageView = new ImageView(image);

            // Optionally, set the size of the ImageView
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);

            // Set the ImageView as the graphic for the button
            likeButton.setGraphic(imageView);
        } else {

            Image image = new Image("file:/C:/Users/rayen/IdeaProjects/startfromthebottom/secondtryjavapidev/src/main/resources/values/icons8-heart-96.png");

            // Create an ImageView from the image
            ImageView imageView = new ImageView(image);

            // Optionally, set the size of the ImageView
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);

            // Set the ImageView as the graphic for the button
            likeButton.setGraphic(imageView);

        }
    }


    private void handleReport(int entityId, String entityType) {
        try {
            int reportCount;
            boolean isPost = entityType.equals("post");

            // Increment the report_count in the database
            if (isPost) {
                sp.incrementReportCount(entityId);
                reportCount = sp.getReportCount(entityId);
            } else {
                throw new IllegalArgumentException("Invalid entity type: " + entityType);
            }

            // Check if the report count has reached the threshold
            if (reportCount >= 5) {
                // If the reported entity is a post
                if (isPost) {
                    sp.Supprimer(entityId);
                    // Retrieve the creator's phone number from the post

                } else {
                    // Delete the comment if it reaches the report threshold
                    sc.Supprimer(entityId);
                }

                // Refresh the UI to reflect the deleted post or comment
                backToAllPosts();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions (e.g., SQL errors) here
        }
    }

    private void handleReportAction(int postId, int reporterId, Button reportButton) {
        // Check if the user has already reported the post
        if (sp.hasReportedPost(postId, reporterId)) {
            System.out.println("User has already reported this post.");
            return;
        }

        // Add the report
        boolean success = sp.addReport(postId, reporterId);
        if (success) {
            // Hide or disable the report button
            reportButton.setVisible(false); // or use reportButton.setDisable(true);
            System.out.println("Post reported successfully. Report button disabled.");
        } else {
            System.out.println("Failed to report the post.");
        }
    }






    private void showPostDetails(post p) {

        postContainer.getChildren().clear();
        commentInput.setVisible(true);

        // Create labels for title, type, and description
        Label titleLabel = new Label("Title: " + p.getTitle_post());
        titleLabel.getStyleClass().add("title-label");
        Label typeLabel = new Label("Type: " + p.getType_post());
        typeLabel.getStyleClass().add("type-label");
        Label descriptionLabel = new Label("Description: " + p.getContenu_post());
        descriptionLabel.getStyleClass().add("description-label");

        // Create a label to display the number of likes
        Label likesLabel = new Label("Likes: " + sp.countLikesForPost(p.getId()));
        likesLabel.getStyleClass().add("likes-label");

        // Create an ImageView for displaying the image
        ImageView imageView = new ImageView(new Image(p.getImage_post()));
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        // Add all components to a VBox to display them vertically
        VBox postDetails = new VBox(titleLabel, typeLabel, descriptionLabel, imageView, likesLabel);
        postDetails.setAlignment(Pos.CENTER);
        postDetails.setSpacing(10);

        // Create a Button to report the post
        Button reportButton = new Button("Report");
        reportButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        // Check if the user has already reported the post
        boolean hasReported = sp.hasReportedPost(p.getId(), loggedInPatient.getId());

        // If the user has reported the post, hide the button
        if (hasReported) {
            reportButton.setVisible(false);
        } else {

            // Otherwise, set an action for the report button
            reportButton.setOnAction(event -> {
                handleReport(p.getId(), "post");

                // Try to add a report
                boolean success = sp.addReport(p.getId(), loggedInPatient.getId());
                if (success) {
                    // Hide the button after the report is successful
                    reportButton.setVisible(false);
                    System.out.println("Report submitted successfully.");
                    System.out.println(sp.getReportCount(p.getId()));
                    System.out.println("+216" + sp.getCreatorPhoneNumber(p.getId()));
                    if(sp.getReportCount(p.getId())>=4){
                        String creatorPhoneNumber = "+216" + sp.getCreatorPhoneNumber(p.getId());

                        // Delete the post if it reaches the report threshold

                        try {

                            String messageContent = "Your post is close be being deleted due to reaching the report threshold.try to modify it accordingly to fit our users terms of service. ";
                            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                            Message message = Message.creator(
                                            new PhoneNumber(creatorPhoneNumber),
                                            new PhoneNumber(TWILIO_PHONE_NUMBER),
                                            messageContent)
                                    .create();

                            System.out.println("SMS sent successfully!");
                        } catch (ApiException e) {
                            System.out.println("Failed to send SMS: " + e.getMessage());
                        }

                        // Send an SMS notification to the creator's phone number
                        //SmsService smsService = new SmsService();

                        System.out.println(creatorPhoneNumber);
                        //smsService.sendSms(creatorPhoneNumber, messageContent);
                    }
                } else {
                    System.out.println("Failed to submit report.");
                }
            });
        }

        postDetails.getChildren().add(reportButton);
        postContainer.getChildren().add(postDetails);
        // Fetch comments for the selected post
        List<comment> comments = sc.affichersingle(p.getId());

        // Display comments

        VBox commentsContainer = new VBox();





        for (comment c : comments) {
            // Create a label for the comment
            Label commentLabel = new Label("Comment: " + c.getContenu_comment());

            // Apply CSS styling to the comment label
            commentLabel.setStyle("-fx-text-fill: #ffffff; /* White color */"
                    + " -fx-font-family: 'Helvetica';"
                    + " -fx-font-size: 14px;");

            // Check if the comment was made by the logged-in user
            if (c.getId_creatorcom() == loggedInPatient.getId()) {
                // Create a "Delete Comment" button
                Button deleteCommentButton = new Button("Delete Comment");
                deleteCommentButton.setStyle("-fx-background-color: #e74c3c; /* Red color */"
                        + " -fx-text-fill: white;"
                        + " -fx-padding: 8px 16px;"
                        + " -fx-background-radius: 5;"
                        + " -fx-font-size: 12px;"
                        + " -fx-font-weight: bold;");

                // Set the action for the delete button
                deleteCommentButton.setOnAction(event -> {
                    // Delete the comment
                    sc.Supprimer(c.getId());

                    // Refresh the post details to reflect the deleted comment
                    showPostDetails(p);
                });

                // Create a "Modify Comment" button
                Button modifyCommentButton = new Button("Modify Comment");
                modifyCommentButton.setStyle("-fx-background-color: #3498db; /* Blue color */"
                        + " -fx-text-fill: white;"
                        + " -fx-padding: 8px 16px;"
                        + " -fx-background-radius: 5;"
                        + " -fx-font-size: 12px;"
                        + " -fx-font-weight: bold;");

                // Set the action for the modify button
                modifyCommentButton.setOnAction(event -> {
                    // Open a new window or dialog to modify the comment
                    openModifyCommentWindow(c,p);
                });

                // Create a container for the comment label and buttons
                HBox commentBox = new HBox(commentLabel, deleteCommentButton, modifyCommentButton);
                commentBox.setSpacing(10); // Add spacing between the components

                // Add the comment box to the comments container
                commentsContainer.getChildren().add(commentBox);
            } else {
                // Add the comment label directly to the comments container
                commentsContainer.getChildren().add(commentLabel);
            }
        }


        // Input area for adding new comments
        // Replace this with your desired input area implementation

        Button addCommentButton = new Button("Add Comment");
        commentErrorLabel = new Label(); // Initialize the error label
        commentErrorLabel.setVisible(false); // Hide the error label initially
        addCommentButton.getStyleClass().add("login_button");
        addCommentButton.setOnAction(event -> {
            String newCommentText = commentInput.getText();
            int minLength = 15; // Define the minimum comment length

            if (newCommentText.length() >= minLength) {
                // Comment length is valid
                commentErrorLabel.setVisible(false); // Hide the error label
                // Filter the new comment
                newCommentText = CommentFilter.filterComment(newCommentText);
                // Add new comment logic here
                sc.ajouter(new comment(loggedInPatient.getId(), p.getId(), "user_name", newCommentText,0));
                showPostDetails(p);
            } else {
                // Comment length is invalid
                commentErrorLabel.setText("Comment must be at least " + minLength + " characters long.");
                commentErrorLabel.setVisible(true);
                commentErrorLabel.setLayoutX(commentInput.getLayoutX());
                commentErrorLabel.setLayoutY(commentInput.getLayoutY() - 20);
                commentErrorLabel.setStyle("-fx-font-size: 14; -fx-text-fill: red;");
                commentInput.setStyle("-fx-border-color: red; -fx-border-radius: 5px; -fx-border-width: 1px;");
            }
        });

        VBox addCommentBox = new VBox(commentInput,commentErrorLabel, addCommentButton);

        // Like Button
        Button likeButton = new Button();
        updateLikeButtonState(likeButton, p.getId());

        likeButton.setOnAction(event -> {
            if (!sp.hasLikedPost(p.getId(), loggedInPatient.getId())) {
                // If the user hasn't liked the post yet, add a like
                boolean success = sp.addLike(p.getId(), loggedInPatient.getId());
                if (success) {
                    // Update the likes count and label
                    p.setLikes_post(p.getLikes_post() + 1);
                    likesLabel.setText("Likes: " + sp.countLikesForPost(p.getId()));
                    // Update UI or provide feedback to the user
                    // likeButton.setText("Unlike");
                    Image image = new Image("file:/C:/Users/rayen/IdeaProjects/startfromthebottom/secondtryjavapidev/src/main/resources/values/icons8-love-96.png");

                    // Create an ImageView from the image
                    ImageView imageVieww = new ImageView(image);

                    // Optionally, set the size of the ImageView
                    imageVieww.setFitHeight(40);
                    imageVieww.setFitWidth(40);

                    // Set the ImageView as the graphic for the button
                    likeButton.setGraphic(imageVieww);

                }
            } else {
                // If the user has already liked the post, remove the like
                boolean success = sp.removeLike(p.getId(), loggedInPatient.getId());
                if (success) {
                    // Update the likes count and label
                    p.setLikes_post(p.getLikes_post() - 1);
                    likesLabel.setText("Likes: " + sp.countLikesForPost(p.getId()));
                    // Update UI or provide feedback to the user
                    Image image = new Image("file:/C:/Users/rayen/IdeaProjects/startfromthebottom/secondtryjavapidev/src/main/resources/values/icons8-heart-96.png");

                    // Create an ImageView from the image
                    ImageView imageVieww = new ImageView(image);

                    // Optionally, set the size of the ImageView
                    imageVieww.setFitHeight(40);
                    imageVieww.setFitWidth(40);

                    // Set the ImageView as the graphic for the button
                    likeButton.setGraphic(imageVieww);
                }
            }
        });

        // Add the like button and likes label to the VBox containing post details and comments
        VBox postWithComments = new VBox(postDetails, commentsContainer, addCommentBox, likeButton);
        postWithComments.setSpacing(10); // Add spacing between components

        // Add the VBox containing post details and comments to the postContainer
        postContainer.getChildren().add(postWithComments);
    }

    public void openModifyCommentWindow(comment c, post p) {
        // Create a new stage for the modification window
        Stage modifyStage = new Stage();
        modifyStage.initModality(Modality.APPLICATION_MODAL);
        modifyStage.setTitle("Modify Comment");

        // Create a text area for editing the comment
        TextArea commentTextArea = new TextArea(c.getContenu_comment());
        commentTextArea.setStyle("-fx-padding: 10px;\n" +
                "    -fx-font-size: 14px;\n" +
                "    -fx-font-family: 'Helvetica';");
        commentTextArea.setWrapText(true);
        commentTextArea.setPrefHeight(100);

        // Create a save button to save the changes
        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #3498db;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-padding: 8px 16px;\n" +
                "    -fx-background-radius: 5;\n" +
                "    -fx-font-size: 14px;\n" +
                "    -fx-font-weight: bold;");
        saveButton.setOnAction(event -> {
            // Get the new comment text
            String newCommentText = commentTextArea.getText();
            newCommentText = CommentFilter.filterComment(newCommentText);

            // Update the comment in the database
            c.setContenu_comment(newCommentText);
            sc.modifier(c); // Implement this method in your service class

            // Close the modify window
            modifyStage.close();

            // Refresh the post details to reflect the modified comment
            showPostDetails(p);
        });

        // Create a layout for the modification window
        VBox modifyLayout = new VBox(commentTextArea, saveButton);
        modifyLayout.setSpacing(10); // Add spacing between the text area and save button
        modifyLayout.setStyle("-fx-spacing: 15px;\n" +
                "    -fx-padding: 20px;");

        // Set the scene and show the modification window
        Scene scene = new Scene(modifyLayout);
        modifyStage.setScene(scene);
        modifyStage.show();
    }


    private void showPostDetailsuser(post p) {


        // Clear postContainer to remove previous post details

        System.out.println("\n" +p.getId());

        postContainer.getChildren().clear();

        // Create labels for title, type, and description
        Label titleLabel = new Label("Title: " + p.getTitle_post());
        titleLabel.getStyleClass().add("title-label");
        Label typeLabel = new Label("Type: " + p.getType_post());
        typeLabel.getStyleClass().add("type-label");
        Label descriptionLabel = new Label("Description: " + p.getContenu_post());
        descriptionLabel.getStyleClass().add("description-label");


        Label likesLabel = new Label("Likes: " + sp.countLikesForPost(p.getId()));
        likesLabel.getStyleClass().add("likes-label");


        // Create an ImageView for displaying the image
        ImageView imageView = new ImageView(new Image(p.getImage_post()));
        imageView.setFitWidth(200); // Set width to 200 (adjust as needed)
        imageView.setFitHeight(200); // Set height to 200 (adjust as needed)

        // Add all components to a VBox to display them vertically
        VBox postDetails = new VBox(titleLabel, typeLabel, descriptionLabel, imageView ,likesLabel);
        postDetails.setAlignment(Pos.CENTER);
        postDetails.setSpacing(10); // Add spacing between components

        // Fetch comments for the selected post
        List<comment> comments = sc.affichersingle(p.getId());

        // Display comments
        VBox commentsContainer = new VBox();
        for (comment c : comments) {

            Label commentLabel = new Label("Comment: " + c.getContenu_comment());
            commentsContainer.getChildren().add(commentLabel);

        }

        // Input area for adding new comments
        // Replace this with your desired input area implementation
       // TextArea commentInput = new TextArea();
        Button addCommentButton = new Button("Add Comment");
        addCommentButton.getStyleClass().add("login_button");
        addCommentButton.setOnAction(event -> {
            String newCommentText = commentInput.getText();
            // Add new comment logic here
            sc.ajouter(new comment(loggedInPatient.getId(), p.getId(), "aaa",newCommentText,0));
            showPostDetails(p);

        });


        VBox addCommentBox = new VBox(commentInput, addCommentButton);

        // Add all components to a VBox to display post details and comments
        VBox postWithComments = new VBox(postDetails, commentsContainer, addCommentBox);
        postWithComments.setSpacing(10); // Add spacing between components

        // Add the VBox containing post details and comments to the postContainer
        postContainer.getChildren().add(postWithComments);

        // Assuming you have a constant image path

        Button deleteButton = new Button("Delete Post");
        deleteButton.getStyleClass().add("login_button");
        deleteButton.setOnAction(event -> {
            // Delete the post logic here
            sp.Supprimer(p.getId()); // Replace deletePost with your actual method
            System.out.println(p.getId());
            // Refresh posts after deletion
            showUserPosts();
        });

        Button modifyButton = new Button("Modify Post");
        modifyButton.getStyleClass().add("login_button");
        modifyButton.setOnAction(event -> {
            // Handle modify button click event
            // Open a window or dialog for modifying the post details
        });


        // Add the delete button to the VBox containing post details and comments
        postWithComments.getChildren().add(deleteButton);

        postWithComments.getChildren().add(modifyButton);

        modifyButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifypost.fxml"));
                Parent root = loader.load();

                // Pass the selected post object to the modify post controller
                modifypost modifyPostController = loader.getController();
                modifyPostController.initData(p);


                // Add event handler for when the modification window closes
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setOnHidden(e -> {
                    // Refresh post details after modification window is closed
                    showPostDetailsuser(p);
                });
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



        // Like Button
        Button likeButton = new Button();
        updateLikeButtonState(likeButton, p.getId());

        likeButton.setOnAction(event -> {
            if (!sp.hasLikedPost(p.getId(), loggedInPatient.getId())) {
                // If the user hasn't liked the post yet, add a like
                boolean success = sp.addLike(p.getId(), loggedInPatient.getId());
                if (success) {



                    p.setLikes_post(p.getLikes_post() + 1);
                    likesLabel.setText("Likes: " + sp.countLikesForPost(p.getId()));
                    // Update UI or provide feedback to the user
                    likeButton.setText("Unlike");




                }
            } else {
                // If the user has already liked the post, remove the like
                boolean success = sp.removeLike(p.getId(), loggedInPatient.getId());
                if (success) {



                    p.setLikes_post(p.getLikes_post() - 1);
                    likesLabel.setText("Likes: " + sp.countLikesForPost(p.getId()));
                    // Update UI or provide feedback to the user
                    likeButton.setText("Like");




                }
            }
        });

// Add the like button to the VBox containing post details and comments
        postWithComments.getChildren().add(likeButton);


    }




    @FXML
    private void showUserPosts() {
        // Example: Load posts by the logged-in user from a database or other source
        List<post> userPosts = sp.getValidatedUserPosts(loggedInPatient.getId()); // Replace getPostsByUser with your actual method


        // Clear postContainer to remove previous post details
        postContainer.getChildren().clear();

        for (post p : userPosts) {
            // Create labels for title, type, and description
            Label titleLabel = new Label("Title: " + p.getTitle_post());
            titleLabel.getStyleClass().add("title-label");
            Label typeLabel = new Label("Type: " + p.getType_post());
            typeLabel.getStyleClass().add("type-label");
            Label descriptionLabel = new Label("Description: " + p.getContenu_post());
            descriptionLabel.getStyleClass().add("description-label");
            Label likesLabel = new Label("Likes: " + sp.countLikesForPost(p.getId()));
            likesLabel.getStyleClass().add("likes-label");


            // Create an ImageView for displaying the image
            ImageView imageView = new ImageView(new Image(p.getImage_post()));
            imageView.setFitWidth(200); // Set width to 200 (adjust as needed)
            imageView.setFitHeight(200); // Set height to 200 (adjust as needed)

            // Create a BorderPane to act as the card
            BorderPane card = new BorderPane();
            card.getStyleClass().add("post-card");
            card.setLeft(titleLabel);
            card.setTop(typeLabel);
            BorderPane.setAlignment(descriptionLabel, Pos.BOTTOM_LEFT);

            card.setBottom(descriptionLabel);
            card.setRight(new StackPane(imageView));

            BorderPane.setAlignment(likesLabel, Pos.BOTTOM_RIGHT);

            card.setBottom(likesLabel);

            // Add event handler to show post details when clicked
            card.setOnMouseClicked(event -> showPostDetailsuser(p));

            // Add the delete button for each post
            Button deleteButton = new Button("Delete Post");
            deleteButton.setOnAction(event -> {
                // Delete the post logic here
                sp.Supprimer(p.getId()); // Replace deletePost with your actual method
                // Refresh posts after deletion
                showUserPosts();
            });

            // Create a VBox to hold the card and delete button
            VBox postWithDeleteButton = new VBox(card);
            postWithDeleteButton.setSpacing(10); // Add spacing between components

            // Add the VBox containing the card and delete button to the postContainer
            postContainer.getChildren().add(postWithDeleteButton);


        }
    }

    private void validateCommentInput(String newValue) {
        int minLength = 15; // Define the minimum comment length

        if (newValue.length() >= minLength) {
            // If comment length is valid, hide the error label and style `commentInput` green
            commentErrorLabel.setVisible(false);
            commentInput.setStyle("-fx-border-color: green; -fx-border-radius: 5px; -fx-border-width: 1px;");
        } else {
            // If comment length is invalid, show the error label and style `commentInput` red
            commentErrorLabel.setText("Comment must be at least " + minLength + " characters long.");
            commentErrorLabel.setVisible(true);
            commentErrorLabel.setLayoutX(commentInput.getLayoutX());
            commentErrorLabel.setLayoutY(commentInput.getLayoutY() - 20);
            commentErrorLabel.setStyle("-fx-font-size: 20; -fx-text-fill: red;");
            commentInput.setStyle("-fx-border-color: red; -fx-border-radius: 5px; -fx-border-width: 5px;");
        }
    }






    @FXML
    private void backToAllPosts() {
        postContainer.getChildren().clear();
        initialize(); // Reload all posts
    }

    @FXML
    private void showUserPostsButtonClicked() {
        // Call the method to display the user's posts
        showUserPosts();

    }
    @FXML
    private void showaddpost(ActionEvent event) {
        // Call the method to display the user's posts
        showaddPosts(event, loggedInPatient);

    }



    public void showaddPosts(ActionEvent event, Patient loggedInPatient) {
        try {
            // Get the source window
            Window window = ((Node) event.getSource()).getScene().getWindow();

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addpost.fxml"));
            Parent root = loader.load();

            // Get the addpost controller
            addpost addPostController = loader.getController();

            // Pass the loggedInPatient to the initData method
            addPostController.initData(loggedInPatient);

            // Set the scene and show the window
            Stage stage = (Stage) window;
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            // Handle any exceptions (e.g., file not found)
            e.printStackTrace();
        }
    }

}
