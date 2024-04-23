package com.example.demo1;

import java.time.LocalDateTime;

public class CreateReclamationController {

    public boolean createReclamation(String nom, String categorie, String sujet, String description) {
        if (nom == null || categorie == null || sujet == null || description == null) {
            System.err.println("Error: Missing required information.");
            return false;
        }

        Reclamation reclamation = new Reclamation();
        reclamation.setNom(nom);
        reclamation.setCategorie(categorie);
        reclamation.setSujet(sujet);
        reclamation.setDescription(description);
        reclamation.setSubdate(LocalDateTime.now());

        boolean success = ReclamationDAO.saveReclamationToDatabase(reclamation);

        if (success) {
            System.out.println("Reclamation created successfully.");
        } else {
            System.err.println("Failed to create reclamation.");
        }

        return success;
    }
}
