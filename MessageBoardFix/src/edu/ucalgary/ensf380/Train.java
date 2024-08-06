package edu.ucalgary.ensf380;
/**
 * this class represents a train in the subway. it gives info about the trains number, current line, current station, coordinates, direction, and also destination 
 * @@author Omar Ahmed <a href="mailto:omar.ahmed3@ucalgary.ca">omar.ahmed3@ucalgary.ca</a>
 * @version 1.1
 * @since 2024-08-03
 */

public class Train {
    private String trainNum; 
    private Line currentLine;
    private Station currentStation; 
    private double x; 
    private double y; 
    private String direction; 
    private Station destination; 

    /**
     * Constructor to initialize the Train with a train number, current line, and current station.
     * 
     * @param trainNum the train number
     * @param currentLine the line the train is currently on
     * @param currentStation the station the train is currently at
     */
    public Train(String trainNum, Line currentLine, Station currentStation) {
        //A constructor to initialize the Trains
        this.trainNum = trainNum;
        this.currentLine = currentLine;
        this.currentStation = currentStation;
        this.x = currentStation.getX();
        this.y = currentStation.getY();
    }
    /**
     * Get the train number.
     * 
     * @return the train number
     */
    public String getTrainNum() {
        
        return trainNum;
    }
    /**
     *gets the line the train is currently on.
     * 
     * @return the current line
     */
    public Line getCurrentLine() {
        
        return currentLine;
    }
    /**
     * gets the current station the train is at.
     * 
     * @return the current station
     */
    public Station getCurrentStation() {
        
        return currentStation;
    }
    /**
     *sets the current station the train is at.
     * 
     * @param currentStation the current station
     */
    public void setCurrentStation(Station currentStation) {
        
        this.currentStation = currentStation;
    }
    /**
     * getting the X coordinate of the train.
     * 
     * @return the X coordinate of the train
     */
    public double getX() {
        
        return x;
    }
    /**
     * setter for the X coordinate of the train.
     * 
     * @param x the X coordinate
     */
    public void setX(double x) {
        //setter for the x coordinate of the train
        this.x = x;
    }
    /**
     *Gets the y coordinate of the train.
     * 
     * @return the Y coordinate of the train
     */
    public double getY() {
        
        return y;
    }
    /**
     *setter for the Y coordinate of the train.
     * 
     * @param y the Y coordinate
     */
    public void setY(double y) {
        
        this.y = y;
    }
    /**
     * this is to get the trains direction from where its moving
     * 
     * @return the direction of the train's movement
     */
    public String getDirection() {
        return direction;
    }
    /**
     *Setter for the direction of the train's movement
     * 
     * @param direction the direction of the train's movement
     */
    public void setDirection(String direction) {
       this.direction = direction;
    }
    /**
     * Gets the destination station of where the train is going to end up
     * 
     * @return the destination station
     */
    public Station getDestination() {
        return destination;
    }
    /**
     * Sets the destination station of the train.
     * 
     * @param destination the destination station
     */
    public void setDestination(Station destination) {
    
        this.destination = destination;
    }
}