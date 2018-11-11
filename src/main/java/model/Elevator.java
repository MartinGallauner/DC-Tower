package model;

import java.util.concurrent.TimeUnit;

public class Elevator {


    // Variables

    private final int id;
    private int currentFloor;
    private boolean isAvailable = true;



    // Constructor

    public Elevator(int id) {
        this.currentFloor = 0;
        this.id = id;
    }

    // Methods

    //TODO: When using threads add a delay to simulate movement
    public void moveUp(){
        this.currentFloor++;
    }


    public void moveDown()  {
        this.currentFloor--;
    }


    public int getCurrentFloor() {
        return currentFloor;
    }


    public int getId() {
        return id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
