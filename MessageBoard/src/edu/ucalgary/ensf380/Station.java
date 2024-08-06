package edu.ucalgary.ensf380;

public class Station {
    private String lineCode; 
    private String stationCode; 
    private String stationName; 
    private double x; 
    private double y;
    private Line line; 

    //A constructor with lineCode, stationCode, stationName, x, y which comes from the csv file
    public Station(String lineCode, String stationCode, String stationName, double x, double y) {
        this.lineCode = lineCode;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.x = x;
        this.y = y;
    }

    //A Constructor with stationCode, stationName, x, y
    public Station(String stationCode, String stationName, double x, double y) {
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.x = x;
        this.y = y;
    }

    public String getLineCode() {
        //Getter for the line code of the station
        return lineCode;
    }

    public String getStationCode() {
        //This Gets the station code
        return stationCode;
    }

    public String getStationName() {
        //Getter for the name of the station
        return stationName;
    }

    public double getX() {
        //Get the X coordinate of the station
        return x;
    }

    public double getY() {
        //Get the Y coordinate of the station
        return y;
    }

    public Line getLine() {
        //gets the line thats assoicated with the station
        return line;
    }

    public void setLine(Line line) {
        //Sets the line associated with the station
        this.line = line;
    }

    public String getName() {
        //Get the name of the station(this method is added for compatibility with the Line class)
        return stationName;
    }
}