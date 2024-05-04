/*package com.esprit.java.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource
{
    private static DataSource instance;
    private Connection connection;

    private final String USERNAME = "root";
    private  final String PASSWORD = "";
    private  final String URL = "jdbc:mysql://127.0.0.1:3306/visita2";

    public DataSource(){
        try{
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("Connecting to DB !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DataSource getInstance() {
        if(instance == null)
            instance = new DataSource();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}

 */



package com.visita.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private static DataSource instance;
    private Connection connection;

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/visita2";

    private DataSource() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connecting to DB !");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database: " + e.getMessage());
            // Handle the exception appropriately (e.g., logging, throwing)
            // You may consider throwing a RuntimeException to propagate the error.
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    public static DataSource getInstance() {
        if (instance == null)
            instance = new DataSource();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    // Close the connection when it's no longer needed
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection to DB closed.");
            } catch (SQLException e) {
                System.out.println("Failed to close the database connection: " + e.getMessage());
                // Handle the exception appropriately (e.g., logging)
            }
        }
    }
}

