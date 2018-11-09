package model;

public class Elevator {


    // Variables

    private int id;
    private int currentFloor;



    // Constructor

    public Elevator(int id) {
        this.currentFloor = 0;
        this.id = id;


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


    public int getId() {
        return id;
    }
}
