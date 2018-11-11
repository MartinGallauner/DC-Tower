import model.Elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ElevatorService {


    // Variables

    private final int numberOfElevators = 7;
    private final int highestFloor = 55;
    private final int lowestFloor = 0;
    List<Elevator> elevatorList;



    // Methods


    public static void main(String[] args) {

        // Set-Up
        ElevatorService elevatorSim = new ElevatorService();
        elevatorSim.setUp();

        // Test
        int available = elevatorSim.checkAvailableElevators();
        elevatorSim.addRequest(0, 55);
        System.out.println("There are " + available + " elevators available");

    }


    private void setUp() {
        elevatorList = new ArrayList<>();
        int i = 1;
        while(i <= numberOfElevators) {
            elevatorList.add(new Elevator(i));
            i++;
        }
    }

    /*
    Core Functionality:
    This method handles new elevator requests
    @param  currentFloor        departure floor
    @param  destinationFloor    arrival floor
    */
    public void addRequest(int currentFloor, int destinationFloor) {

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

    /*
    The search algorithm is the core subject of this coding challenge
    @param currentFloor     The departure floor of the request
    @return                 The most efficient elevator to handle the request

     */
    // TODO: I have to split this up
    private Elevator searchBestElevator(int departureFloor) {

        // Check if an elevator is ready
        Elevator readyToGo = readyToGo(departureFloor);
        if(readyToGo != null) {
            return readyToGo;
        }

        List<Elevator> availableElevators = getAvailableElevators();
        // If only 1 elevator is available, choose it
        if(availableElevators.size() == 1) {
            return availableElevators.get(0);
        }

        // TODO: if no elevator is available closestElevator will be null!
        return getClosestElevator(availableElevators,departureFloor);
    }


    private Elevator readyToGo(int departureFloor) {

        // Check if an elevator is standing in this floor
        for (Elevator elevator : elevatorList) {
            if (elevator.getCurrentFloor() == departureFloor) {
                return elevator;
            }
        }


        return null;
    }

    private List<Elevator> getAvailableElevators() {

        // Check which elevators are without Job,
        List<Elevator> availableElevators = new ArrayList<>();
        for (Elevator elevator : elevatorList) {
            if (elevator.isAvailable()) {
                availableElevators.add(elevator);
            }
        }

        return availableElevators;
    }

    private Elevator getClosestElevator(List<Elevator> availableElevators, int departureFloor) {

        Elevator closestElevator = null;
        int bestDistance = highestFloor;
        for(Elevator elevator : availableElevators) {
            int distance;
            int elevatorPosition = elevator.getCurrentFloor();
            if(elevatorPosition < departureFloor) {
                distance = departureFloor - elevatorPosition;
            }
            else {
                distance = elevatorPosition - departureFloor;
            }
            if(distance < bestDistance) {
                closestElevator = elevator;
                bestDistance = distance;
            }

        }
        return closestElevator;


    }

    /*
    Helper Method:
    Sends the elevator to another floor
    @param elevator             Which elevator should move?
    @param destinationFloor     To which floor should the elevator move?
    TODO: Not sure if I should reuse the name destinationFloor
     */

    /**
     *
     * @param elevator
     * @param destinationFloor
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
