package com.example.visitajava.Test;

import com.example.visitajava.Entity.CategoryEvent;
import com.example.visitajava.Entity.Evenement;
import com.example.visitajava.Services.ServiceCategoryEvent;
import com.example.visitajava.Services.ServiceEvenement;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ServiceEvenement evenementService = new ServiceEvenement();
        ServiceCategoryEvent serviceCategoryEvent = new ServiceCategoryEvent();
        try {
           // Adding an event
            Evenement newEvent = new Evenement();
            newEvent.setImage_evenement("Event Image");
            newEvent.setType_evenement("new type");
            newEvent.setNom_evenement("NewEvent1");
            newEvent.setLieu_evenement("Event Location");
            // Set the date and time of the event
            LocalDateTime eventDateTime = LocalDateTime.of(2024, 2, 12, 20, 30);
            newEvent.setDate_debut(eventDateTime);
            newEvent.setDate_fin(eventDateTime);
            newEvent.setNb_participants(12);
            newEvent.setCategory_id(1);
           evenementService.ajouter(newEvent);
            System.out.println("Event added successfully.");
           /* newEvent.setId(27);
            evenementService.modifier(newEvent);
            System.out.println("Event update successfully.");*/
          /*  newEvent.setId(27);
            evenementService.supprimer(newEvent);
            System.out.println("Event deleted successfully");*/

            // category
            CategoryEvent categoryEvent = new CategoryEvent();
            categoryEvent.setName("new bnj");
            categoryEvent.setDescription("hello");

           /*serviceCategoryEvent.ajouter(categoryEvent);
            System.out.println("category added successfully.");*/

           /* categoryEvent.setId(1);
            serviceCategoryEvent.modifier(categoryEvent);
            System.out.println("modified category");*/

           /* categoryEvent.setId(1);
            serviceCategoryEvent.supprimer(categoryEvent);
            System.out.println("deleted category");*/

            System.out.println("Category:");
            List<CategoryEvent> categoryEvents = serviceCategoryEvent.afficher();
            for (CategoryEvent categoryEvenement : categoryEvents) {
                System.out.println("ID: " + categoryEvent.getId());
                System.out.println("Name: " + categoryEvent.getName());
                System.out.println("Description: " + categoryEvenement.getDescription()) ;
            }
            System.out.println();

         /*   System.out.println("Events:");
            List<Evenement> events = evenementService.afficher();
            for (Evenement event : events) {
                System.out.println("ID: " + event.getId());
                System.out.println("image: " + event.getImage_evenement());
                System.out.println("type: " + event.getType_evenement());
                System.out.println("Name: " + event.getNom_evenement());
                System.out.println("Location: " + event.getLieu_evenement());
                System.out.println("Start Date: " + event.getDate_debut());
                System.out.println("End Date: " + event.getDate_fin());
                System.out.println("nombre des participants" + event.getNb_participants());
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
