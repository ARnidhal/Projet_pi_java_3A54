package controllers;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import models.User;

public class EmailSender {
    // Email configuration

    public static final String SMTP_HOST = "sandbox.smtp.mailtrap.io"; // SMTP server host
    public static final int SMTP_PORT = 2525; // SMTP server port
    public static final String EMAIL_USERNAME = "ad0248e11ac398"; // Your email username
    public static final String EMAIL_PASSWORD = "2e5cc44a153acc"; // Your email password

    public static void sendVerificationCode(Session session, User user, String verificationCode) {
        try {
            // Create a message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject("Verification Code");
            message.setText("Your verification code is: " + verificationCode);

            // Send the message
            Transport.send(message);

            System.out.println("Verification code sent successfully to " + user.getEmail());
        } catch (MessagingException e) {
            System.out.println("Failed to send verification code to " + user.getEmail());
            e.printStackTrace();
        }
    }
}

