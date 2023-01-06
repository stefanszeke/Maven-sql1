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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASS);

            // insert into table before SELECT
            Statement insertInto = conn.createStatement();
            insertInto.executeUpdate("INSERT INTO companies (id,name) VALUES (4,'Nintendo')");
            insertInto.executeUpdate("INSERT INTO games (name, genre, release_year, company_id) VALUES ('Super Mario Bros','platformer', 1985, 4)");
            
            // select from table
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM games JOIN companies ON (games.company_id = companies.id)");

            // print out table
            int index = 1;
            while (rs.next()) {
                if(rs.getString("games.name").equals("Super Mario Bros")) { System.out.print(" -> NEW: ");}
                System.out.println(index + ": [" + rs.getString("name") + "] made by [" + rs.getString("companies.name") + "] released in " + rs.getString("release_year"));
                index++;
            }
            
            // delete from table
            Statement deleteFrom = conn.createStatement();
            deleteFrom.executeUpdate("DELETE FROM games WHERE name = 'Super Mario Bros'");
            deleteFrom.executeUpdate("DELETE FROM companies WHERE name = 'Nintendo'");
            
            // reset cursor
            rs = stmt.executeQuery("SELECT games.name as 'game', games.release_year as 'year', companies.name as 'company' FROM games JOIN companies ON (games.company_id = companies.id)");
            
            System.out.println();

            // print out table
            index = 1;
            while (rs.next()) {
                System.out.println(index + ": [" + rs.getString("game") + "] made by [" + rs.getString("company") + "] released in " + rs.getString("year"));
                index++;
            }
            
            conn.close();


        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
