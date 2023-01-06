package com.example;

import java.sql.*;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Dotenv dotenv = Dotenv.load();

        
        System.out.println( "Hello World!" );

        final String DB_URL = dotenv.get("DB_URL");
        final String DB_NAME = dotenv.get("DB_NAME");
        final String DB_USER = dotenv.get("DB_USER");
        final String DB_PASS = dotenv.get("DB_PASS");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASS);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM games JOIN companies ON games.company_id = companies.id");





            while (rs.next()) {
                System.out.println("[" + rs.getString("name") + "] made by [" + rs.getString("companies.name") + "] released in " + rs.getString("release_year"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
