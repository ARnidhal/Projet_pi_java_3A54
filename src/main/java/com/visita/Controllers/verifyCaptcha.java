package com.visita.Controllers;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
//import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.GenericData;

import java.util.Map;
public class verifyCaptcha {

   /* // Clé secrète reCAPTCHA
    private static final String RECAPTCHA_SECRET = "VOTRE_CLE_SECRETE_RECAPTCHA";

    // URL de l'API reCAPTCHA
    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public static boolean verifyCaptcha(String captchaResponse) {
        try {
            // Préparez les paramètres de la requête pour la vérification du captcha
            GenericData postData = new GenericData();
            postData.put("secret", RECAPTCHA_SECRET);
            postData.put("response", captchaResponse);

            // Créez une requête HTTP POST vers l'API reCAPTCHA
            HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
            HttpRequest request = requestFactory.buildPostRequest(
                    new GenericUrl(RECAPTCHA_VERIFY_URL),
                    new UrlEncodedContent(postData)
            );

            // Parse la réponse JSON
            request.setParser(new JsonObjectParser(new JacksonFactory()));
            HttpResponse response = request.execute();
            Map<String, Object> json = response.parseAsMap();

            // Vérifiez si la réponse a été réussie
            return (boolean) json.get("success");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
*/

}
