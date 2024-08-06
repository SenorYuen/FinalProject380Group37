package edu.ucalgary.ensf380;
/**
 *The MapParser class is responsible for parsing station information from a CSV file
 * @@author Omar Ahmed <a href="mailto:omar.ahmed3@ucalgary.ca">omar.ahmed3@ucalgary.ca</a>
 * @version 1.1
 * @since 2024-08-03
 */

public class Station {
    private String lineCode; 
    private String stationCode; 
    private String stationName; 
    private double x; 
    private double y;
    private Line line; 

   
    /**
     * A constructor with lineCode, stationCode, stationName, x, y which comes from the csv file
     * @param lineCode the code of the line the station is on
     * @param stationCode the code of the station
     * @param stationName the name of the station
     * @param x the X coordinate of the station
     * @param y the Y coordinate of the station
     */
    public Station(String lineCode, String stationCode, String stationName, double x, double y) {
        this.lineCode = lineCode;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.x = x;
        this.y = y;
    }
    /**
     * A Constructor with stationCode, stationName, x, y
     * @param stationCode the code of the station
     * @param stationName the name of the station
     * @param x the X coordinate of the station
     * @param y the Y coordinate of the station
     */
    public Station(String stationCode, String stationName, double x, double y) {
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.x = x;
        this.y = y;
    }
    /**
     *Getter for the line code of the station
     * 
     * @return the line code of the station
     */
    public String getLineCode() {
        
        return lineCode;
    }
    /**
     *This Gets the station code
     * 
     * @return the station code
     */
    public String getStationCode() {
       
        return stationCode;
    }
    /**
     *Getter for the name of the station
     * 
     * @return the name of the station
     */
    public String getStationName() {
        
        return stationName;
    }
    /**
     *Get the X coordinate of the station
     * 
     * @return the X coordinate of the station
     */
    public double getX() {
        
        return x;
    }
    /**
     * Get the Y coordinate of the station
     * 
     * @return the Y coordinate of the station
     */
    public double getY() {
        
        return y;
    }
    /**
     *gets the line thats assoicated with the station
     * 
     * @return the line associated with the station
     */
    public Line getLine() {
        
        return line;
    }
    /**
     *Sets the line associated with the station
     * 
     * @param line the line to be associated with the station
     */
    public void setLine(Line line) {
        
        this.line = line;
    }
    /**
     *Get the name of the station(this method is added to be compatible with the Line class)
     * 
     * 
     * @return the name of the station
     */
    public String getName() {
        //Get the name of the station(this method is added for compatibility with the Line class)
        return stationName;
    }
}