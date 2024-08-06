package edu.ucalgary.ensf380;

import java.util.ArrayList;
import java.util.List;
/**
 * this class represnets a subway line with stations along the line
 * has methods to add and get stations and get the name of the line
 * @@author Omar Ahmed <a href="mailto:omar.ahmed3@ucalgary.ca">omar.ahmed3@ucalgary.ca</a>
 * @version 1.1
 * @since 2024-08-03
 */

public class Line {

    private String name;
    private List<Station> stations;
     /**
     * Constructor that initializes a line with a given name.
     * 
     * @param name the name of the line
     */
    public Line(String name) {
        this.name = name;
        this.stations = new ArrayList<>();
    }
    /**
     * this method gets the name of the line.
     * 
     * @return the name of the line
     */
    public String getName() {
        
        return name;
    }
    /**
     * this adds the station to the line.
     * 
     * @param station the station to be added to the line
     */
    public void addStation(Station station) {
        
        stations.add(station);
    }
    /**
     * //This getter returns all the stations on the line as a list
     * 
     * @return a list of stations on the line
     */
    public List<Station> getStations() {
        return stations;
    }
     /**
     *This method gets a station using its name 
     * 
     * @param name the name of the station we want
     * @return the station with the name we wnat, or return null
     */
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

