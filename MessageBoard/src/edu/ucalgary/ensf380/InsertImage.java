package edu.ucalgary.ensf380;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class InsertImage {

    public static void main(String[] args) {
        List<String> imagePaths = Arrays.asList(
            "/Users/farisjanjua/Desktop/data/BMW.jpg",
            "/Users/farisjanjua/Desktop/data/Chicken.jpg",
            "/Users/farisjanjua/Desktop/data/Cat.jpg",
            "/Users/farisjanjua/Desktop/data/Travis.jpg"
        );

        String url = "jdbc:mysql://localhost:3306/SubwayScreenAdvertisements";
        String user = "root";
        String password = "Leomessi10@";

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