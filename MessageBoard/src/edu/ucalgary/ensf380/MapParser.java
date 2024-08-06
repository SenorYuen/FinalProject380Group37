package edu.ucalgary.ensf380;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapParser {
    public List<Station> parseStations(String filePath) {
        //This method anaylzes the stations from a CSV file

        List<Station> stations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); //This skips the header in the csv file
            while ((line = reader.readLine()) != null) {
                
                String[] details = line.split(",");
                String lineCode = details[0]; //The is the line code of the station
                String stationCode = details[1]; //This is the Station code
                String stationName = details[2]; // his is the Name of the station
                double x = Double.parseDouble(details[3]); //The X coordinate of the station
                double y = Double.parseDouble(details[4]); //The Y coordinate of the station

        
                //This creates a new station with the parsed details
                Station station = new Station(lineCode, stationCode, stationName, x, y);
                stations.add(station); //This will add the station to the list
            }
        } catch (IOException e) {
            e.printStackTrace(); //Print the stack trace if an exception occurs
        }
        return stations; //This returns the list of stations
    }
}