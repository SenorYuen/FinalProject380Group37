package edu.ucalgary.ensf380;

public class Station {
    private String lineCode; // Line code of the station
    private String stationCode; // Station code
    private String stationName; // Name of the station
    private double x; // X coordinate of the station
    private double y; // Y coordinate of the station
    private Line line; // Line object associated with the station

    // Constructor with lineCode, stationCode, stationName, x, y
    public Station(String lineCode, String stationCode, String stationName, double x, double y) {
        this.lineCode = lineCode;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.x = x;
        this.y = y;
    }

    // Constructor with stationCode, stationName, x, y
    public Station(String stationCode, String stationName, double x, double y) {
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.x = x;
        this.y = y;
    }

    public String getLineCode() {
        // Get the line code of the station
        return lineCode;
    }

    public String getStationCode() {
        // Get the station code
        return stationCode;
    }

    public String getStationName() {
        // Get the name of the station
        return stationName;
    }

    public double getX() {
        // Get the X coordinate of the station
        return x;
    }

    public double getY() {
        // Get the Y coordinate of the station
        return y;
    }

    public Line getLine() {
        // Get the line associated with the station
        return line;
    }

    public void setLine(Line line) {
        // Set the line associated with the station
        this.line = line;
    }

    public String getName() {
        // Get the name of the station (this method is added for compatibility with the Line class)
        return stationName;