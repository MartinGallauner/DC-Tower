import service.ElevatorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    public static void main(String[] args) {
        ElevatorService elevatorService = new ElevatorService();
        ExecutorService threadPool = Executors.newFixedThreadPool(7);
        System.out.println(elevatorService.checkAvailableElevators());
        elevatorService.addRequest(0,55);
        elevatorService.addRequest(1,55);
        elevatorService.addRequest(54,0);
        System.out.println(elevatorService.checkAvailableElevators());
        threadPool.shutdown();
    }



}
