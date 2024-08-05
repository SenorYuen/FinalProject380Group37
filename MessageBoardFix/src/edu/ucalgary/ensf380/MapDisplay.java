package edu.ucalgary.ensf380;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MapDisplay extends JPanel {
    private List<Train> trains; // List of trains to display
    private Train currentTrain; // Current train to highlight
    private ImageIcon icon; // Icon for the subway map image

    public MapDisplay(List<Train> trains, Train currentTrain) {
        // Initialize the MapDisplay with a list of trains and the current train
        this.trains = trains;
        this.currentTrain = currentTrain;
        this.icon = new ImageIcon("data/Trains.png"); // Ensure the path is correct
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set background color
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the subway map image in the center of the panel
        int imageX = 0, imageY = 0, imageWidth = 0, imageHeight = 0;
        if (icon.getImage() != null) {
            imageWidth = icon.getIconWidth();
            imageHeight = icon.getIconHeight();
            int panelWidth = this.getWidth();
            int panelHeight = this.getHeight();
            imageX = (panelWidth - imageWidth) / 2;
            imageY = (panelHeight - imageHeight) / 2;

            g.drawImage(icon.getImage(), imageX, imageY, this);
        }

        // Draw the trains with adjusted coordinates
        for (Train train : trains) {
            if (train == currentTrain) {
                g.setColor(Color.GREEN); // Highlight the current train in green
            } else {
                g.setColor(Color.RED); // Other trains in red
            }
            // Adjust train coordinates to fit the image position and scaling
            int trainX = imageX + (int) (train.getX() * imageWidth / 1200.0); // Adjust based on original image scale
            int trainY = imageY + (int) (train.getY() * imageHeight / 720.0); // Adjust based on original image scale
            g.fillOval(trainX, trainY, 10, 10); // Draw the train as a filled oval
        }
    }

    public void updateTrains(List<Train> trains, Train currentTrain) {
        // Update the list of trains and the current train, then repaint
        this.trains = trains;
        this.currentTrain = currentTrain;
        repaint();
    }
}
































