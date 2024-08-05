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

public class RetrieveImage {

    private static List<String> imagePaths = new ArrayList<>();
    private static int currentIndex = 0;
    private static JLabel label;

    public static void main(String[] args) {
        fetchImagePaths();
        if (!imagePaths.isEmpty()) {
            displayImage();
        } else {
            System.out.println("No advertisements found.");
        }
    }

    public static void fetchImagePaths() {
        String url = "jdbc:mysql://localhost:3306/SubwayScreenAdvertisements";
        String user = "root";
        String password = "Leomessi10@";

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

    public static List<String> getImagePaths() {
        return new ArrayList<>(imagePaths);
    }

    private static void displayImage() {
        JFrame frame = new JFrame();
        label = new JLabel();
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        Timer timer = new Timer(10000, e -> updateImage());
        timer.start();
        updateImage(); // Display the first image immediately
    }

    private static void updateImage() {
        if (currentIndex >= imagePaths.size()) {
            currentIndex = 0;
        }
        ImageIcon icon = new ImageIcon(imagePaths.get(currentIndex));
        label.setIcon(icon);
        currentIndex++;
    }
}