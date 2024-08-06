package edu.ucalgary.ensf380;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.Timer;
import java.util.ArrayList;

public class SubwaySimulator {
    private TrainPositionUpdater trainPositionUpdater;
    private List<Train> trains;
    private MapDisplay mapDisplay;
    private Train currentTrain;
    private String outputDir;
    private Timer timer;

    public SubwaySimulator(List<Station> stations, List<Train> trains, MapDisplay mapDisplay, Train currentTrain, String outputDir) {
        this.trains = trains;
        this.mapDisplay = mapDisplay;
        this.trainPositionUpdater = new TrainPositionUpdater(stations);
        this.currentTrain = currentTrain;
        this.outputDir = outputDir;
    }

    public void startSimulation() {
        // Initial update
        updatePositions();

        // Display route for the current train
        displayTrainRoute(currentTrain);

        // Set up a timer to update positions every 15 seconds
        if (timer == null) { // Ensure only one timer is created
            timer = new Timer(15000, e -> {
                updatePositions();
                displayTrainRoute(currentTrain);
            });
            timer.start();
            System.out.println("Simulation started. Timer set to update every 15 seconds.");
        } else {
            System.out.println("Simulation already running.");
        }
    }

    public void updatePositions() {
        System.out.println("Updating positions...");
        for (Train train : trains) {
            updateTrainPosition(train);
        }
        mapDisplay.updateTrains(trains, currentTrain);
        printTrainPositions();
    }

    private void updateTrainPosition(Train train) {
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
    
    public static ArrayList<String> displayTrainRoute(Train train) {
        Line line = train.getCurrentLine();
        List<Station> stations = line.getStations();
        int currentIndex = stations.indexOf(train.getCurrentStation());
        int numStations = stations.size();
        String direction = train.getDirection();
    
        ArrayList<String> routeInfo = new ArrayList<>();
    
        routeInfo.add("Train " + train.getTrainNum() + " Direction: " + direction);
    
        // Add the previous station if it exists
        if (currentIndex > 0) {
            Station previousStation = stations.get((currentIndex - 1 + numStations) % numStations);
            routeInfo.add("Previous Station: " + previousStation.getStationName());
        } else {
            routeInfo.add("Previous Station: None (this is the first station)");
        }
    
        // Add the next four stations
        for (int i = 1; i <= 4; i++) {
            int nextIndex = (currentIndex + i) % numStations;
            Station nextStation = stations.get(nextIndex);
            routeInfo.add("Next Station " + i + ": " + nextStation.getStationName());
        }
    
        return routeInfo;
    }
}