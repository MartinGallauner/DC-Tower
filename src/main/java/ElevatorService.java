import model.Elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class ElevatorService {


    private final int numberOfElevators = 7;
    private final int highestFloor = 55;
    private final int lowestFloor = 0;
    private List<Elevator> elevatorList;
    private ExecutorService threadPool = Executors.newFixedThreadPool(7);




    public ElevatorService() {
        setUp();
    }


    public static void main(String[] args) {
        ElevatorService elevatorService = new ElevatorService();
        ExecutorService threadPool = Executors.newFixedThreadPool(7);
        System.out.println(elevatorService.checkAvailableElevators());

        elevatorService.addRequest(0,55);
        elevatorService.addRequest(0,55);
        elevatorService.addRequest(0,55);
        elevatorService.addRequest(0,55);
        System.out.println(elevatorService.checkAvailableElevators());

        threadPool.shutdown();
    }




    /**
     * Handles new request
     * @param currentFloor Departure floor of the elevator request
     * @param destinationFloor Destination of the elevator request
     */
    public void addRequest(int currentFloor, int destinationFloor) {

        //TODO: this should throw an exception
        if(!requestIsValid(currentFloor, destinationFloor)) {
            return;
        }

        Runnable thisTask = () -> {
            Elevator bestElevator = searchBestElevator(currentFloor);

            bestElevator.setAvailable(false);
            if(bestElevator.getCurrentFloor() != currentFloor) {
                sendElevator(bestElevator, currentFloor);
            }

            // TODO: When using Threads, wait until the elevator is there
            sendElevator(bestElevator, destinationFloor);
            bestElevator.setAvailable(true);
        };

        threadPool.execute(thisTask);
    }


    private void setUp() {
        elevatorList = new ArrayList<>();
        int i = 1;
        while(i <= numberOfElevators) {
            elevatorList.add(new Elevator(i));
            i++;
        }
    }


    /**
     * Counts all elevators not handling a request
     * @return number of all available elevators
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


    private boolean requestIsValid(int currentFloor, int destinationFloor) {

        if (currentFloor < lowestFloor || currentFloor > highestFloor || destinationFloor < lowestFloor
                || destinationFloor > highestFloor) {
            return false;
        }

        return true;
    }


    private Elevator searchBestElevator(int departureFloor) {

        Elevator readyToGo = readyToGo(departureFloor);
        if(readyToGo != null) {
            return readyToGo;
        }
        List<Elevator> availableElevators = getAvailableElevators();
        if(availableElevators.size() == 1) {
            return availableElevators.get(0);
        }
        // TODO: if no elevator is available closestElevator will be null!
        return getClosestElevator(availableElevators,departureFloor);
    }


    private Elevator readyToGo(int departureFloor) {

        for (Elevator elevator : elevatorList) {
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

    //TODO: The try/catch block is ugly as hell
    private void sendElevator(Elevator elevator, int destinationFloor) {
        if(elevator.getCurrentFloor() < destinationFloor) {
            sendUp(elevator, destinationFloor);
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        else {
            sendDown(elevator, destinationFloor);
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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


}
