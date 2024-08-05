package edu.ucalgary.ensf380;

public class Train {
    private String trainNum; // Train number
    private Line currentLine; // Current line the train is on
    private Station currentStation; // Current station the train is at
    private double x; // X coordinate of the train
    private double y; // Y coordinate of the train
    private String direction; // Direction of the train's movement
    private Station destination; // Destination station of the train

    public Train(String trainNum, Line currentLine, Station currentStation) {
        // Constructor to initialize the Train
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
        // Get the current line the train is on
        return currentLine;
    }

    public Station getCurrentStation() {
        // Get the current station the train is at
        return currentStation;
    }

    public void setCurrentStation(Station currentStation) {
        // Set the current station the train is at
        this.currentStation = currentStation;
    }

    public double getX() {
        // Get the X coordinate of the train
        return x;
    }

    public void setX(double x) {
        // Set the X coordinate of the train
        this.x = x;
    }

    public double getY() {
        // Get the Y coordinate of the train
        return y;
    }

    public void setY(double y) {
        // Set the Y coordinate of the train
        this.y = y;
    }

    public String getDirection() {
        // Get the direction of the train's movement
        return direction;
    }

    public void setDirection(String direction) {
        // Set the direction of the train's movement
        this.direction = direction;
    }

    public Station getDestination() {
        // Get the destination station of the train
        return destination;
    }

    public void setDestination(Station destination) {
        // Set the destination station of the train
        this.destination = destination;
    }
}