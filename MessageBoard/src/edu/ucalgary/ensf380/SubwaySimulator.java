package edu.ucalgary.ensf380;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SubwaySimulator {
    private TrainPositionUpdater trainPositionUpdater; // Updates the positions of the trains
    private List<Train> trains; // List of trains in the simulation
    private MapDisplay mapDisplay; // Map display for visual representation
    private Train currentTrain; // Current train to be highlighted
    private String outputDir; // Directory for output files

    public SubwaySimulator(List<Station> stations, List<Train> trains, MapDisplay mapDisplay, Train currentTrain, String outputDir) {
        // Constructor to initialize the SubwaySimulator
        this.trains = trains;
        this.mapDisplay = mapDisplay;
        this.trainPositionUpdater = new TrainPositionUpdater(stations);
        this.currentTrain = currentTrain;
        this.outputDir = outputDir;
    }

    public void startSimulation() {
        // Method to start the subway simulation

        // Initial update
        updatePositions();

        // Set up a timer to update positions every 15 seconds
        javax.swing.Timer timer = new javax.swing.Timer(15000, e -> updatePositions());
        timer.start();
    }

    public void updatePositions() {
        // Method to update train positions using the latest data

        for (Train train : trains) {
            updateTrainPosition(train);
        }
        mapDisplay.updateTrains(trains, currentTrain);
        printTrainPositions();
    }

    private void updateTrainPosition(Train train) {
        // Method to update the position of a single train

        Line line = train.getCurrentLine();
        List<Station> stations = line.getStations();
        int currentIndex = stations.indexOf(train.getCurrentStation());
        int nextIndex;

        if ("forward".equals(train.getDirection())) {
            nextIndex = (currentIndex + 1) % stations.size();
        } else {
            nextIndex = (currentIndex - 1 + stations.size()) % stations.size();
        }

        train.setCurrentStation(stations.get(nextIndex));
        train.setX(train.getCurrentStation().getX());
        train.setY(train.getCurrentStation().getY());
    }

    private void printTrainPositions() {
        // Method to print the current positions of the trains to the console and write them to a file

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputDir + "/train_positions.txt"))) {
            for (Train train : trains) {
                String position = String.format("Train %s is at station %s (%f, %f)%n",
                        train.getTrainNum(), train.getCurrentStation().getStationName(),
                        train.getX(), train.getY());
                System.out.print(position);
                writer.write(position);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}