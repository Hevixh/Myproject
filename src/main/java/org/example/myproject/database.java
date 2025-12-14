package org.example.myproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    public static Connection connectDb() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5433/student";
            String user = "postgres";
            String password = "4214";

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to PostgreSQL successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
        return connection;
    }
}

