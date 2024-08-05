package edu.ucalgary.ensf380;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SubwayScreenApp extends JPanel{
    private static List<Station> stationsList = new ArrayList<>(); // List to store stations
    private static List<Line> lineList = new ArrayList<>(); // List to store lines
    private static List<Train> trainList = new ArrayList<>(); // List to store trains
    private static Line red, blue, green; // Line objects for red, blue, and green lines
    private static Train currentTrain; // The current train to be highlighted
    private static JFrame mapFrame;

    public List<Station> getStationsList() {
		return stationsList;
	}

	public static void setStationsList(List<Station> stationsList) {
		SubwayScreenApp.stationsList = stationsList;
	}

	public List<Train> getTrainList() {
		return trainList;
	}

	public static void setTrainList(List<Train> trainList) {
		SubwayScreenApp.trainList = trainList;
	}

	public SubwayScreenApp(int currentTrainNumber) {

        // Initialize the subway system
        initializeSubwaySystem("data/subway.csv", "data/Trains_1680832574555.csv");

        // Get stations and trains
        List<Station> stations = stationsList;
        List<Train> trains = trainList;

        mapFrame = new JFrame("Subway Map");
        currentTrain = trains.get(currentTrainNumber - 1); // Train numbers are 1-based
        MapDisplay mapDisplay = new MapDisplay(trains, currentTrain);
        mapFrame.add(mapDisplay);
        mapFrame.setSize(500, 275);

        //frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        mapFrame.setVisible(true);

        SubwaySimulator simulator = new SubwaySimulator(stations, trains, mapDisplay, currentTrain, "out");
        simulator.startSimulation();

        javax.swing.Timer timer = new javax.swing.Timer(15000, e -> {
            simulator.updatePositions();
        });
        timer.start();
    }

    public static void initializeSubwaySystem(String stationsFilePath, String trainsFilePath) {
        // Method to initialize the subway system by loading stations and trains from files
        loadStationsFromFile(stationsFilePath);
        initializeLines();
        assignStationsToLines();
        loadTrainsFromFile(trainsFilePath);
    }

    private static void loadStationsFromFile(String filePath) {
        // Method to load stations from a CSV file

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                // Split the line into details
                String[] details = line.split(",");
                String stationCode = details[3]; // Station code
                String stationName = details[4]; // Station name
                double xCoord = Double.parseDouble(details[5]); // X coordinate
                double yCoord = Double.parseDouble(details[6]); // Y coordinate

                // Create a new station with the parsed details
                Station station = new Station(details[1], stationCode, stationName, xCoord, yCoord);
                stationsList.add(station); // Add the station to the list
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace if an exception occurs
        }
    }

    private static void initializeLines() {
        // Method to initialize the subway lines
        red = new Line("Red"); // Initialize red line
        blue = new Line("Blue"); // Initialize blue line
        green = new Line("Green"); // Initialize green line
    }

    private static void assignStationsToLines() {
        // Method to assign stations to their respective lines

        for (Station station : stationsList) {
            String lineInitial = station.getStationCode().substring(0, 1); // Get the line initial from the station code
            switch (lineInitial) {
                case "R":
                    red.addStation(station); // Add station to red line
                    station.setLine(red); // Set the station's line to red
                    break;
                case "B":
                    blue.addStation(station); // Add station to blue line
                    station.setLine(blue); // Set the station's line to blue
                    break;
                case "G":
                    green.addStation(station); // Add station to green line
                    station.setLine(green); // Set the station's line to green
                    break;
            }
        }
    }

    private static void loadTrainsFromFile(String filePath) {
        // Method to load trains from a CSV file

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                // Split the line into details
                String[] details = line.split(",");
                String trainNum = details[1]; // Train number
                String lineName = details[0]; // Line name
                String stationCode = details[2]; // Station code
                String direction = details[3]; // Direction

                // Get the line object based on the line name
                Line lineObj = null;
                switch (lineName) {
                    case "R":
                        lineObj = red;
                        break;
                    case "B":
                        lineObj = blue;
                        break;
                    case "G":
                        lineObj = green;
                        break;
                }

                // Find the initial station for the train
                Station initialStation = null;
                for (Station station : stationsList) {
                    if (station.getStationCode().equals(stationCode)) {
                        initialStation = station;
                        break;
                    }
                }

                // Create a new train with the parsed details and add it to the list
                if (lineObj != null && initialStation != null) {
                    Train train = new Train(trainNum, lineObj, initialStation);
                    train.setDirection(direction);
                    trainList.add(train);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace if an exception occurs
        }
    }
    
    public File getFrameImage() {
    	JFrame frameToConvert = mapFrame;
        frameToConvert.pack();
        frameToConvert.setVisible(true);
        
        BufferedImage image = new BufferedImage(500, 275, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = image.createGraphics();
        frameToConvert.paint(g2d);
        g2d.dispose();
        
        try {
            File framePath = new File("frame.png");
            ImageIO.write(image, "png", framePath);
            return framePath;
        } catch (IOException e) {
        	e.printStackTrace();
        	return null;
        }
        
    }
}




























