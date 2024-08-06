package edu.ucalgary.ensf380;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class InsertImage {
 /**
 * inserts image paths into the database.
 * This class connects to a MySQL database and allows us to insert images into it
 * @author Faris <a href="mailto:faris.janjua@ucalgary.ca">faris.janjua@ucalgary.ca</a>
 * @version 1.1
 * @since 1.0
 */
    public InsertImage(String sqlPassword) {
        List<String> imagePaths = Arrays.asList(
            "..\\AdvertisementImages\\BMW.jpg",
            "..\\AdvertisementImages\\Subway.jpg",
            "..\\AdvertisementImages\\Chicken.jpg",
            "..\\AdvertisementImages\\Burger.jpg"
        );
  /**
     * Constructs an InsertImage object with the database password
     * @param password The password for connecting to the MySQL database
    */
        String url = "jdbc:mysql://localhost:3306/SubwayScreenAdvertisements";
        String user = "root";
        String password = sqlPassword;

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO images (image_path) VALUES (?)";

            for (String imagePath : imagePaths) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, imagePath);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}