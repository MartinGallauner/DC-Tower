package service;

import model.Elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Provides the logic/algorithm for the elevator simulation and updates the elevator states.
 */


public class ElevatorService {

    private static final int NUMBER_OF_ELEVATORS = 7;
    private static final int HIGHEST_FLOOR = 55;
    private static final int LOWEST_FLOOR = 0;

    private List<Elevator> elevatorList;
    private ExecutorService threadPool = Executors.newFixedThreadPool(NUMBER_OF_ELEVATORS);

    public ElevatorService() {
        elevatorList = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_ELEVATORS; i++) {
            elevatorList.add(new Elevator(i));
        }
    }

    /**
     * Handles new request
     *
     * @param currentFloor     Departure floor of the elevator request
     * @param destinationFloor Destination of the elevator request
     */
    public void addRequest(int currentFloor, int destinationFloor) {
        if (!isRequestValid(currentFloor, destinationFloor)) {
            // TODO consider returning a boolean or result enum to indicate success/failure/other or throw possibly throw an exception
            return;
        }

        Elevator bestElevator = searchBestElevator(currentFloor);
        bestElevator.setAvailable(false);

        Runnable thisTask = () -> {
            if (bestElevator.getCurrentFloor() != currentFloor) {
                sendElevator(bestElevator, currentFloor);
            }
            sendElevator(bestElevator, destinationFloor);
            bestElevator.setAvailable(true);
        };

        threadPool.execute(thisTask);
    }

    /**
     * Counts all elevators not handling a request
     *
     * @return number of all available elevators
     */
    public int checkAvailableElevators() {
        int availableElevators = 0;

        for (Elevator elevator : elevatorList) {
            if (elevator.isAvailable()) {
                availableElevators++;
            }
        }
        return availableElevators;
    }

    private boolean isRequestValid(int currentFloor, int destinationFloor) {
        return currentFloor >= LOWEST_FLOOR
                && currentFloor <= HIGHEST_FLOOR
                && destinationFloor >= LOWEST_FLOOR
                && destinationFloor <= HIGHEST_FLOOR;
    }

    /**
     * @param departureFloor The floor where the elevator is needed.
     * @return Returns the next available elevator next to the departure floor.
     */
    private Elevator searchBestElevator(int departureFloor) {
        Elevator elevator;

        do {
            elevator = readyToGo(departureFloor); //Is an elevator on this floor and available?
            if (elevator != null) {
                return elevator;
            }

            List<Elevator> availableElevators = getAvailableElevators(); //Creates a list of all available elevators
            if (availableElevators.size() == 1) {
                return availableElevators.get(0);   //If there is only 1 elevator available - take this one
            }
            //Of all available elevators, which one is waiting closest?
            elevator = getClosestElevator(availableElevators, departureFloor);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } while (elevator == null); // This search algorithm is repeated until it finds an elevator.
        return elevator;
    }

    private Elevator readyToGo(int departureFloor) {
        for (Elevator elevator : getAvailableElevators()) {
            if (elevator.getCurrentFloor() == departureFloor) {
                return elevator;
            }
        }
        return null;
    }

    private List<Elevator> getAvailableElevators() {
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
        int bestDistance = HIGHEST_FLOOR + 1; //The highest possible distance between elevator and the departureFloor

        for (Elevator elevator : availableElevators) {
            int distance;
            int elevatorPosition = elevator.getCurrentFloor();
            // This if/else statement calculates the actual distance between THIS elevator and the departureFloor
            if (elevatorPosition < departureFloor) {
                distance = departureFloor - elevatorPosition;
            } else {
                distance = elevatorPosition - departureFloor;
            }
            //If this distance is lower than bestDistance - this distance is the new bestDistance
            if (distance < bestDistance) {
                closestElevator = elevator;
                bestDistance = distance;
            }
        }
        return closestElevator;
    }

    private void sendElevator(Elevator elevator, int destinationFloor) {
        if (elevator.getCurrentFloor() < destinationFloor) {
            sendUp(elevator, destinationFloor);
        } else {
            sendDown(elevator, destinationFloor);
        }
    }

    private void sendUp(Elevator elevator, int destinationFloor) {
        while (elevator.getCurrentFloor() < destinationFloor) {
            elevator.moveUp();
        }
    }

    private void sendDown(Elevator elevator, int destinationFloor) {
        while (elevator.getCurrentFloor() > destinationFloor) {
            elevator.moveDown();
        }
    }

    public void shutdown() {
        threadPool.shutdown();
    }
}
