package service;

import model.Elevator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


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
     * @param currentFloor Departure floor of the elevator request
     * @param destinationFloor Destination of the elevator request
     */
    public void addRequest(int currentFloor, int destinationFloor) {

        if(!requestIsValid(currentFloor, destinationFloor)) {
            return;
        }

        Elevator bestElevator = searchBestElevator(currentFloor);
        bestElevator.setAvailable(false);

        Runnable thisTask = () -> {
            if(bestElevator.getCurrentFloor() != currentFloor) {
                sendElevator(bestElevator, currentFloor);
            }
            sendElevator(bestElevator, destinationFloor);
            bestElevator.setAvailable(true);
        };

        threadPool.execute(thisTask);
    }

    /**
     * Counts all elevators not handling a request
     * @return number of all available elevators
     */
    public int checkAvailableElevators() {
        int availableElevators = 0;

        for(Elevator elevator : elevatorList) {
            if(elevator.isAvailable()) {
                availableElevators++;
            }
        }
        return availableElevators;
    }

    private boolean requestIsValid(int currentFloor, int destinationFloor) {

        if (currentFloor < LOWEST_FLOOR || currentFloor > HIGHEST_FLOOR || destinationFloor < LOWEST_FLOOR
                || destinationFloor > HIGHEST_FLOOR) {
            return false;
        }
        return true;
    }

    private Elevator searchBestElevator(int departureFloor) {
        Elevator elevator = null;

        do {
            elevator = readyToGo(departureFloor);
            if(elevator != null) {
                return elevator;
            }

            List<Elevator> availableElevators = getAvailableElevators();
            if(availableElevators.size() == 1) {
                return availableElevators.get(0);
            }

            elevator = getClosestElevator(availableElevators,departureFloor);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } while (elevator == null);
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
        int bestDistance = HIGHEST_FLOOR +1;

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

    private void sendElevator(Elevator elevator, int destinationFloor) {
        if(elevator.getCurrentFloor() < destinationFloor) {
            sendUp(elevator, destinationFloor);
            }
        else {
            sendDown(elevator, destinationFloor);
        }
    }

    private void sendUp(Elevator elevator, int destinationFloor) {
        while(elevator.getCurrentFloor() < destinationFloor) {
            elevator.moveUp();
        }
    }

    private void sendDown(Elevator elevator, int destinationFloor) {
        while(elevator.getCurrentFloor() > destinationFloor) {
            elevator.moveDown();
        }
    }

    public void shutdown() {
        threadPool.shutdown();
    }
}
