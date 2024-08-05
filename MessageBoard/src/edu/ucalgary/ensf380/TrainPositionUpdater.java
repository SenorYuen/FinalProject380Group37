package edu.ucalgary.ensf380;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainPositionUpdater {
    private List<Station> stations; // List of stations

    public TrainPositionUpdater(List<Station> stations) {
        // Constructor to initialize the TrainPositionUpdater
        this.stations = stations;
    }

    public List<Train> parseTrainPositions(String filePath) {
        // Method to parse train positions from a CSV file
        List<Train> trains = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            reader.readNext(); // Skip header

            int trainId = 1; // Initialize train ID

            while ((line = reader.readNext()) != null) {
                if (line.length < 7) {
                    // Skip invalid lines
                    System.err.printf("Skipping invalid line: %s%n", String.join(",", line));
                    continue;
                }

                try {
                    double x = Double.parseDouble(line[5]); // Parse X coordinate
                    double y = Double.parseDouble(line[6]); // Parse Y coordinate

                    // Find the nearest station to the parsed coordinates
                    Station nearestStation = findNearestStation(x, y);
                    Train train = new Train(String.valueOf(trainId), nearestStation.getLine(), nearestStation);
                    train.setX(x);
                    train.setY(y);
                    trains.add(train); // Add the train to the list
                    trainId++; // Increment train ID for the next train
                } catch (NumberFormatException e) {
                    // Skip lines with number format issues
                    System.err.printf("Skipping invalid line due to number format issue: %s%n", String.join(",", line));
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace(); // Print the stack trace if an exception occurs
        }

        return trains; // Return the list of trains
    }

    private Station findNearestStation(double x, double y) {
        // Method to find the nearest station to given coordinates
        Station nearestStation = null;
        double minDistance = Double.MAX_VALUE;

        for (Station station : stations) {
            double distance = Math.sqrt(Math.pow(station.getX() - x, 2) + Math.pow(station.getY() - y, 2));
            if (distance < minDistance) {
                minDistance = distance;
                nearestStation = station;
            }
        }

        return nearestStation; // Return the nearest station
    }
}












































