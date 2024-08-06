package edu.ucalgary.ensf380;

public class Train {
    private String trainNum; 
    private Line currentLine;
    private Station currentStation; 
    private double x; 
    private double y; 
    private String direction; 
    private Station destination; 

    public Train(String trainNum, Line currentLine, Station currentStation) {
        //A constructor to initialize the Trains
        this.trainNum = trainNum;
        this.currentLine = currentLine;
        this.currentStation = currentStation;
        this.x = currentStation.getX();
        this.y = currentStation.getY();
    }

    public String getTrainNum() {
        // Get the train number
        return trainNum;
    }

    public Line getCurrentLine() {
        //gets whatever line the train is currently on
        return currentLine;
    }

    public Station getCurrentStation() {
        //gets the current station the train is at
        return currentStation;
    }

    public void setCurrentStation(Station currentStation) {
        //sets the current station the train is at
        this.currentStation = currentStation;
    }

    public double getX() {
        //getting for the x coordinate of the train
        return x;
    }

    public void setX(double x) {
        //setter for the x coordinate of the train
        this.x = x;
    }

    public double getY() {
        //Gets the y coordinate of the train
        return y;
    }

    public void setY(double y) {
        //Setter for the the Y coordinate of the train
        this.y = y;
    }

    public String getDirection() {
        //this is to get the trains direction from where its moving
        return direction;
    }

    public void setDirection(String direction) {
        //Setter for the direction of the train's movement
        this.direction = direction;
    }

    public Station getDestination() {
        //Gets the destination station of where the train is going to end up
        return destination;
    }

    public void setDestination(Station destination) {
        //Sets the destination station of the train
        this.destination = destination;
    }
}