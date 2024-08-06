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
        this.icon = new ImageIcon("data/Trains.png"); // This gives the file directory to the image on the screen
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //This sets the background of the screen to white
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

       
        //This makes sure the image is in the center of the screen
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
                g.setColor(Color.GREEN); //Whatever current train you choose as will become green
            } else {
                g.setColor(Color.RED); //All the other trains will be red
            }
            //This adjust the train coordinates so its in scale with its position
            int trainX = imageX + (int) (train.getX() * imageWidth / 1200.0); 
            int trainY = imageY + (int) (train.getY() * imageHeight / 800.0); 
            g.fillOval(trainX, trainY, 10, 10); //This chooses the shape of the train on the screen
        }
    }

    public void updateTrains(List<Train> trains, Train currentTrain) {
        //This will update the list of trains and the current train, then paint it again
        this.trains = trains;
        this.currentTrain = currentTrain;
        repaint();
    }
}