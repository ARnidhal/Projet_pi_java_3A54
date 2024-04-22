package com.visita.test;

import com.visita.models.Category;
import com.visita.models.Service;
import com.visita.models.ReservationService;
import com.visita.services.ServiceService;
import com.visita.services.Categoryservice;
import com.visita.services.ReservationSrvService;

import java.util.Date;
import java.util.List;

public class MainProg {
    public static void main(String[] args) {
        // Création d'une instance de ServiceService
        ServiceService sr = new ServiceService();
        Categoryservice ct = new Categoryservice();
        ReservationSrvService reservationService = new ReservationSrvService();



// Création d'une instance de Date représentant la date actuelle
        Date dateActuelle = new Date();
        // Conversion de la date actuelle en java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(dateActuelle.getTime());


         //category add
      ct.ajouter(new Category("category1","category description 1","fhddf"));
        ct.ajouter(new Category("category2","category description 2","fhddf"));
        ct.ajouter(new Category("category3","category description 3","fhddf"));
//affichage category
        System.out.println("Liste des catégories :");
        afficherCategories(ct);

        // Modification de la troisième catégorie
        List<Category> categories = ct.afficher();
        Category categoryAModifier = categories.get(2); // La troisième catégorie (indice 2 car les indices commencent à 0)
        categoryAModifier.setNom("NouveauNom");
        categoryAModifier.setDescription("NouvelleDescription");
        categoryAModifier.setIcon("NouvelIcone");
        ct.modifier(categoryAModifier);

        // Affichage des catégories après la modification
        System.out.println("Liste des catégories après la modification de la troisième catégorie :");
        afficherCategories(ct);

        // Suppression de la deuxième catégorie
       Category categoryASupprimer = categories.get(1); // La deuxième catégorie (indice 1)
        ct.supprimer(categoryASupprimer);

        // Affichage des catégories après la suppression
        System.out.println("Liste des catégories après la suppression de la deuxième catégorie :");
        afficherCategories(ct);

// Ajout de trois services avec la méthode ajouter
        sr.ajouter(new Service("", "Description du childcare", sqlDate,"",128, false));
        sr.ajouter(new Service("", "Description du radiology", sqlDate,"",128, false));
        sr.ajouter(new Service("", "Description du nursing", sqlDate,"",128, true));


        // Affichage des services
        System.out.println("Liste des services après l'ajout :");
        afficherServices(sr);

        // Récupération du troisième service pour le modifier
        List<Service> services = sr.afficher();
        Service serviceAModifier = services.get(1); // Le troisième service (indice 2 car les indices commencent à 0)
        serviceAModifier.setNom("GYNECOLOGY");
        serviceAModifier.setDescription(" description GYNECOLOGY");
        serviceAModifier.setImage(" description GYNECOLOGY");
        sr.modifier(serviceAModifier);

        // Affichage des services après la modification
        System.out.println("Liste des services après la modification du troisième service :");
        afficherServices(sr);

        // Suppression du deuxième service
        Service serviceASupprimer = services.get(0); // Le deuxième service (indice 1)
        sr.supprimer(serviceASupprimer);

        // Affichage des services après la suppression
        System.out.println("Liste des services après la suppression du deuxième service :");
        afficherServices(sr);



        // Affichage des services après la suppression
        System.out.println("Liste des services après la suppression du deuxième service :");
        afficherServices(sr);


        // Ajout de trois réservations
        reservationService.ajouter(new ReservationService(169, "ahmed", "email1@test.com"));
        reservationService.ajouter(new ReservationService(169, "aziz", "email2@test.com"));
        reservationService.ajouter(new ReservationService(169, "nidhal", "email3@test.com"));


        // Affichage des réservations avant la suppression
        System.out.println("Liste des réservations avant la suppression de la deuxième :");
        afficherReservations(reservationService);

        // Suppression de la deuxième réservation
        ReservationService reservationASupprimer = new ReservationService(2);
        reservationService.supprimer(reservationASupprimer);
        System.out.println("Suppression de la deuxième réservation...");

        // Affichage des réservations après la suppression
        System.out.println("Liste des réservations après la suppression de la deuxième :");
        afficherReservations(reservationService);
    }




    // Méthode utilitaire pour afficher les services
    private static void afficherServices(ServiceService serviceService) {
        List<Service> services = serviceService.afficher();
        for (Service service : services) {
            System.out.println("ID : " + service.getId());
            System.out.println("Nom : " + service.getNom());
            System.out.println("Description : " + service.getDescription());
            System.out.println("Date de création : " + service.getDateCreation());
            System.out.println("Nom de la catégorie : " + service.getCategory_nom());
            System.out.println("Actif : " + service.isActive());
            System.out.println();
        }
    }

    // Méthode utilitaire pour afficher les catégories
    private static void afficherCategories(Categoryservice categoryService) {
        List<Category> categories = categoryService.afficher();
        for (Category category : categories) {
            System.out.println(category);
        }
        System.out.println();
    }


    private static void afficherReservations(ReservationSrvService reservationService) {
        // Récupérer la liste des réservations
        List<ReservationService> reservations = reservationService.afficher();

        // Boucle à travers chaque réservation dans la liste
        for (ReservationService reservation : reservations) {
            // Afficher les détails de la réservation
            System.out.println("Détails de la réservation:");
            System.out.println("ID de la réservation : " + reservation.getId());
            System.out.println("Nom : " + reservation.getNom());
            System.out.println("Email : " + reservation.getEmail());
            System.out.println("Nom du service réservé : " + reservation.getService_nom());
            System.out.println(); // Ligne vide pour séparer les réservations
        }
    }

}





