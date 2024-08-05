package edu.ucalgary.ensf380;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapParser {
    public List<Station> parseStations(String filePath) {
        // Method to parse stations from a CSV file

        List<Station> stations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                // Split the line into details
                String[] details = line.split(",");
                String lineCode = details[0]; // Line code of the station
                String stationCode = details[1]; // Station code
                String stationName = details[2]; // Name of the station
                double x = Double.parseDouble(details[3]); // X coordinate
                double y = Double.parseDouble(details[4]); // Y coordinate

                // Create a new station with the parsed details
                Station station = new Station(lineCode, stationCode, stationName, x, y);
                stations.add(station); // Add the station to the list
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace if an exception occurs
        }
        return stations; // Return the list of stations
    }
}