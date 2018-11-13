package model;

import java.util.concurrent.TimeUnit;


public class Elevator {

    private final int id;
    private int currentFloor;
    private boolean isAvailable = true;

    public Elevator(int id) {
        this.currentFloor = 0;
        this.id = id;
    }

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
            TimeUnit.SECONDS.sleep(1);
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

    public synchronized boolean isAvailable() {
        return isAvailable;
    }

    public synchronized void setAvailable(boolean available) {
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
