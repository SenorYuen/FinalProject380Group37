package edu.ucalgary.ensf380;

import java.util.ArrayList;
import java.util.List;

public class Line {
    //This class represents a subway line with the stations

    private String name;
    private List<Station> stations;

    public Line(String name) {
        //A constructor that initalizes a line with the name
        this.name = name;
        this.stations = new ArrayList<>();
    }

    public String getName() {
        //This method that gets the name of the line
        return name;
    }

    public void addStation(Station station) {
        //This adds the station to the line
        stations.add(station);
    }

    public List<Station> getStations() {
        //This getter returns all the stations on the line 
        return stations;
    }

    public Station getStationByName(String name) {
        //This method gets a station using its name 
        for (Station station : stations) {
            if (station.getName().equals(name)) {
                return station;
            }
        }
        return null;
    }
}

