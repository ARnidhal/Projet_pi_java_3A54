package com.esprit.java.tests;

import java.sql.Time;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.esprit.java.models.Rapport;
import com.esprit.java.models.Rendezvous;
import com.esprit.java.services.RapportService;
import com.esprit.java.services.RendezvousService;
import com.esprit.java.utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class main
{
    public static void main(java.lang.String[] args) throws ParseException {

        //DataSource ds = new DataSource();
        /*////////////////////////////////////RENDEZVOUS//////////////////////////////////////////////////////////////////
 ////////////////////////test ajout rv//////////////////////////////////////////////
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Date date = new Date(dateFormat.parse("05-10-2002").getTime());
        Time time = new Time(timeFormat.parse("13:00").getTime());

        RendezvousService rv = new RendezvousService();
        rv.ajouter(new Rendezvous(1,"wejden",56656578,date,time,"fdffd",true,"wejden@gmail.com",55));



           //////////////////test supprimer Rv/////////////////////////
       //RendezvousService rv=new RendezvousService();
        Rendezvous rendezvous = new Rendezvous();
        rendezvous.setId(64);
        rendezvous.setMedecin_id(1);
        rendezvous.setFullname("Nouveau Nom Prenom");
        rendezvous.setTel(987654321);
        rendezvous.setDate(date);
        rendezvous.setTime(time); // Remplacez cette valeur par l'heure appropriée
        rendezvous.setNote("Nouvelle Note");
        rendezvous.setEtat(false);
        rendezvous.setEmail("nouveauemail@example.com");
        rendezvous.setRapport_id(34); //
        rv.supprimer(rendezvous);





        ////////////////////////updaterendezvous////////////////////////
        /*Rendezvous rendezvous = new Rendezvous();
        rendezvous.setId(50);
        rendezvous.setMedecin_id(1);
        rendezvous.setFullname("Nouveau Nom Prenom");
        rendezvous.setTel(987654321);
        rendezvous.setDate(date);
        rendezvous.setTime(time); // Remplacez cette valeur par l'heure appropriée
        rendezvous.setNote("Nouvelle Note");
        rendezvous.setEtat(false);
        rendezvous.setEmail("nouveauemail@example.com");
        rendezvous.setRapport_id(34); // Remp
        rv.modifier(rendezvous);


        ///////////////////affichage ////////////

                 System.out.println(rv.afficher());

         */


        ////////////////////////////////////////////////////////Rapport//////////////////////////////

        RapportService r=new RapportService();
        r.ajouter(new Rapport(37,"lkdk","ldmlkd"));
        //r.supprimer(new Rapport(57,87,"ddfd","jdkd"));
       /* List<Rapport> rapports = r.afficher();
        for (Rapport rapport : rapports) {
            System.out.println("ID du rapport : " + rapport.getId());
            System.out.println("ID du rendez-vous associé : " + rapport.getRendzvous_id());
            System.out.println("Type : " + rapport.getType());
            System.out.println("Note : " + rapport.getNote());
            System.out.println("-------------");
        }*/
        r.modifier((new Rapport(59,37,"lkdk","edited") ));

    }
}
