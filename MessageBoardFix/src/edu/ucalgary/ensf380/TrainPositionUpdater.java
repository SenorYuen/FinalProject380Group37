package edu.ucalgary.ensf380;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class updates the train positions based on the csv files that we have
 * @@author Omar Ahmed <a href="mailto:omar.ahmed3@ucalgary.ca">omar.ahmed3@ucalgary.ca</a>
 * @version 1.1
 * @since 2024-08-03
 */
public class TrainPositionUpdater {
    private List<Station> stations; 
    /**
     * Constructor to initialize the TrainPositionUpdater
     * 
     * @param stations the list of stations
     */
    public TrainPositionUpdater(List<Station> stations) {
        this.stations = stations;
    }
    /**
     * Parses train positions from a CSV file.
     * 
     * @param filePath the path to the CSV file containing the train positions
     * @return a list of Train objects that has been analyzed from the CSV file
     */
    public List<Train> parseTrainPositions(String filePath) {
        
        List<Train> trains = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            reader.readNext(); //skip th header in the csv file

            int trainId = 1; //Initialize the train ID

            while ((line = reader.readNext()) != null) {
                if (line.length < 7) {
                    //Skip invalid lines in the csv file
                    System.err.printf("Skipping invalid line: %s%n", String.join(",", line));
                    continue;
                }

                try {
                    double x = Double.parseDouble(line[5]); //Parse the X coordinate
                    double y = Double.parseDouble(line[6]); //Parse the Y coordinate

                    //Find the nearest station that coresponds to the coordinates above
                    Station nearestStation = findNearestStation(x, y);
                    Train train = new Train(String.valueOf(trainId), nearestStation.getLine(), nearestStation);
                    train.setX(x);
                    train.setY(y);
                    trains.add(train); //add the train to the list
                    trainId++; // increment to find the next train
                } catch (NumberFormatException e) {
                    //skip whatever line that has number format issues
                    System.err.printf("Skipping invalid line due to number format issue: %s%n", String.join(",", line));
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace(); //Print the stack trace if an exception occurs
        }

        return trains; //Return the list of trains
    }
    /**
     * this method Finds the nearest station to the coordinates we got
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the nearest station to the given coordinates
     */
    private Station findNearestStation(double x, double y) {
        
        Station nearestStation = null;
        double minDistance = Double.MAX_VALUE;

        for (Station station : stations) {
            double distance = Math.sqrt(Math.pow(station.getX() - x, 2) + Math.pow(station.getY() - y, 2));
            if (distance < minDistance) {
                minDistance = distance;
                nearestStation = station;
            }
        }

        return nearestStation; //Return the nearest station
    }
}
