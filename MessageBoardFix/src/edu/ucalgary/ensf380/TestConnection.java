package edu.ucalgary.ensf380;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * sing JDBC It will try to connect to the SubwayScreenAdvertisements database
 *and will mention its connection ststus
 * @author Faris <a href="mailto:faris.janjua@ucalgary.ca">faris.janjua@ucalgary.ca</a>
 * @version 1.0
 * @since 1.0
 */

public class TestConnection {
    public static final String url = "jdbc:mysql://localhost:3306/SubwayScreenAdvertisements";
    public static final String username = "root";
    public static final String password = "pleasework";
    /**
     * Establised connection to database and prints status of connection
     *@param args command-line arguments
     */

    public static void main(String[] args) {
        Connection conn = null;
        try {
            //load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Create the connection
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successful!");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection failed.");
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            //Ensure closed connection
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close the connection.");
                    e.printStackTrace();
                }
            }
        }
    }
}