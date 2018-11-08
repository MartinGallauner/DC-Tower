package model;

public class Elevator {


    // Variables

    private int currentFloor;




    // Constructor

    public Elevator() {
        this.currentFloor = 0;


    }

    // Methods

    public void moveUp() {
        this.currentFloor++;
    }


    public void moveDown() {
        this.currentFloor--;
    }


    public int getCurrentFloor() {
        return currentFloor;
    }


}
