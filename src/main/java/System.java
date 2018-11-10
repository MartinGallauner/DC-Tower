import model.Elevator;

import java.util.ArrayList;
import java.util.List;

public class System {


    // Variables

    int numberOfElevators = 7;
    List<Elevator> elevatorList;
    private int highestFloor = 55;
    private int lowestFloor = 0;



    // Methods


    public static void main(String[] args) {

        // Set-Up
        System elevatorSim = new System();
        elevatorSim.setUp();



    }


    private void setUp() {
        elevatorList = new ArrayList<>();
        int i = 1;
        while(i <= numberOfElevators) {
            elevatorList.add(new Elevator(i));
            i++;
        }
    }


    public void addRequest(int currentFloor, int destinationFloor, boolean movesDown) {

        // Check if request is valid
        if(!requestIsValid(currentFloor, destinationFloor)) {
            return;
        }


        // Search for the most efficient elevator
        Elevator bestElevator = searchBestElevator(currentFloor);

        // Mark Elevator not available
        bestElevator.setAvailable(false);

        // Send Elevator to currentFloor
        if(bestElevator.getCurrentFloor() != currentFloor) {
            sendElevator(bestElevator, currentFloor);
        }

        // TODO: When using Threads, wait until the elevator is there
        // Send Elevator to destinationFloor
        sendElevator(bestElevator, destinationFloor);

        // Mark Elevator available
        bestElevator.setAvailable(true);


    }

    /*
    Core Functionality:
    Counts all elevators not handling a request.
    @return number of available elevators
     */

    public int checkAvailableElevators() {
        int availableElevators = 0;
        for(Elevator elevator : elevatorList) {
            if(elevator.isAvailable() == true) {
                availableElevators++;
            }
        }
        return availableElevators;
    }

    /*
    Helper Method:
    Checks if the request is valid
     */

    // TODO: I'm sure there is a smarter way to do this
    private boolean requestIsValid(int currentFloor, int destinationFloor) {
        if (currentFloor < 0 || currentFloor > 55) {
            return false;
        }

        if (destinationFloor < 0 || destinationFloor > 55) {
            return false;
        }
        return true;
    }

    // TODO: The search algorithm is the core subject of this coding challenge
    private Elevator searchBestElevator(int currentFloor) {
        // Check if an elevator is standing in this floor

        // Check if an elevator is moving towards this floor, if more than 1 - which is first?

        // Check which elevators are without Job, if more than 1 - which is closest?

        // Which elevator is the next without job?

        return null;
    }

    /*
    Helper Method:
    Sends the elevator to another floor
    @param elevator             Which elevator should move?
    @param destinationFloor     To which floor should the elevator move?
    TODO: Not sure if I should reuse the name destinationFloor
     */

    private void sendElevator(Elevator elevator, int destinationFloor) {



        if(elevator.getCurrentFloor() < destinationFloor) {
            sendUp(elevator, destinationFloor);
        }
        else {
            sendDown(elevator, destinationFloor);
        }
    }

    /*
    Helper method:
    Sends the elevator up until he reaches his destination
     */

    private void sendUp(Elevator elevator, int destinationFloor) {
        while(elevator.getCurrentFloor() < destinationFloor) {
            elevator.moveUp();
        }
    }

    /*
    Helper method:
    Sends the elevator down until he reaches his destination
     */

    private void sendDown(Elevator elevator, int destinationFloor) {
        while(elevator.getCurrentFloor() > destinationFloor) {
            elevator.moveDown();
        }
    }


}
