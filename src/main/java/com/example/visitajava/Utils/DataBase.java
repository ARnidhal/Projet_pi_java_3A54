package com.example.visitajava.Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    private static DataBase instance ;
    private final String URL ="jdbc:mysql://localhost:3306/visita";
    private final String USERNAME="root";
    private final String PASSWORD ="";

    static Connection cnx ;


    private DataBase(){

        try {
            cnx = DriverManager.getConnection(URL,USERNAME,PASSWORD);

            System.out.println("Connected ...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("not connected ");
        }

    }
    public static DataBase getInstance(){
        if (instance == null)
            instance = new DataBase();

        return instance;
    }
    public static Connection getCnx(){
        return cnx;
    }
}
