package edu.ucalgary.ensf380;

import java.util.ArrayList;
import java.util.List;

public class Line {
    // Class representing a subway line with stations

    private String name;
    private List<Station> stations;

    public Line(String name) {
        // Constructor to initialize a line with a name
        this.name = name;
        this.stations = new ArrayList<>();
    }

    public String getName() {
        // Getter method for the name of the line
        return name;
    }

    public void addStation(Station station) {
        // Method to add a station to the line
        stations.add(station);
    }

    public List<Station> getStations() {
        // Getter method to return the list of stations on the line
        return stations;
    }

    public Station getStationByName(String name) {
        // Method to get a station by its name
        for (Station station : stations) {
            if (station.getName().equals(name)) {
                return station;
            }
        }
        return null;
    }
}

