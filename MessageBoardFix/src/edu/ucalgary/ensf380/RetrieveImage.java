package edu.ucalgary.ensf380;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * This class will handle establishing a connection to the database, and retreiving the images. 
 * @author Faris <a href="mailto:faris.janjua@ucalgary.ca">faris.janjua@ucalgary.ca</a>
 * @version 1.0
 * @since 1.0
 */
public class RetrieveImage {

    private static List<String> imagePaths = new ArrayList<>();
    private static int currentIndex = 0;
    private static JLabel label;
    
    /**
     * The constructor will establish a connection, and act as a helper function by calling everything else.
     * @param sqlPassword will be sourced from ScreenDisplay, and will be used to authenticate the user's SQL connection
     */
    public RetrieveImage(String sqlPassword) {
        fetchImagePaths(sqlPassword);
        if (!imagePaths.isEmpty()) {
            //displayImage();
        } else {
            System.out.println("No advertisements found.");
        }
    }

    /**
     * This method will handle making the connection to the SQL database and sending a query to the database to retrieve the images.
     * @param sqlPassword will be sourced from the constructor, and will authenticate the user's connection.
     */
    public static void fetchImagePaths(String sqlPassword) {
        String url = "jdbc:mysql://localhost:3306/SubwayScreenAdvertisements";
        String user = "root";
        String password = sqlPassword;

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT image_path FROM images";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    imagePaths.add(rs.getString("image_path"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This is a getter to retrieve the list of strings that golds the image paths.
     * @return an array list of strings/
     */
    public List<String> getImagePaths() {
        return new ArrayList<>(imagePaths);
    }
}