
package com.visita.controllers;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSMS
{
    /*
    // Vos identifiants Twilio
    public static final String ACCOUNT_SID = "AC19405307081f214bf11a9ffe75ed0720";
    public static final String AUTH_TOKEN = "99e853f84841b4ea55f34d3278896063";

    public static void main(String[] args) {
        // Initialiser Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Envoyer un message
        Message message = Message.creator(
                        new PhoneNumber("+21629304408"),
                        new PhoneNumber("+13342343809"),
                        "Votre rendezvous est confirmé \nMerci pour votre confiance \nBonne journée !")
                .create();

        // Afficher l'identifiant du message
        System.out.println("Message SID: " + message.getSid());
    }

      */
    // Initialisation de Twilio avec vos identifiants
    public static final String ACCOUNT_SID = "AC19405307081f214bf11a9ffe75ed0720";
    public static final String AUTH_TOKEN = "99e853f84841b4ea55f34d3278896063";

    // Méthode pour envoyer un SMS
    public static void envoyerSMS(String numeroDestinataire, String message) {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            // Envoi du SMS avec Twilio
            Message.creator(new PhoneNumber(numeroDestinataire), new PhoneNumber("+13342343809"), message).create();
            System.out.println("SMS envoyé avec succès à " + numeroDestinataire);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi du SMS : " + e.getMessage());
        }
    }

}
