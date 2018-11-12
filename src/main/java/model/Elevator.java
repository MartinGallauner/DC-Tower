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
        transitTime();
        this.currentFloor++;
    }


    public void moveDown()  {
        transitTime();
        this.currentFloor--;
    }

    private void transitTime() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    @Override
    public String toString() {
        return "Elevator{" +
                "id=" + id +
                ", currentFloor=" + currentFloor +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
