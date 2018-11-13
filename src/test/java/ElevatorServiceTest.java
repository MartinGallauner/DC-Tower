import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatorServiceTest {


    @Test
    public void testCheckAvailableElevatorsNoRequest() {
        ElevatorService elevatorService = new ElevatorService();
        ExecutorService threadPool = Executors.newFixedThreadPool(7);
        waitForIt();
        assertEquals(7, elevatorService.checkAvailableElevators());
        threadPool.shutdown();
    }


    @Test
    public void testElevatorServiceSingleRequest() {
        ElevatorService elevatorService = new ElevatorService();
        ExecutorService threadPool = Executors.newFixedThreadPool(7);
        elevatorService.addRequest(0,37);
        waitForIt();
        assertEquals(6, elevatorService.checkAvailableElevators());
        threadPool.shutdown();
    }

    @Test
    public void testElevatorServiceSmallRequest() {
        ElevatorService elevatorService = new ElevatorService();
        ExecutorService threadPool = Executors.newFixedThreadPool(7);
        elevatorService.addRequest(0,35);
        elevatorService.addRequest(35,0);
        waitForIt();
        assertEquals(5, elevatorService.checkAvailableElevators());
        threadPool.shutdown();
    }


    @Test
    public void testElevatorServiceBigRequest() {
        ElevatorService elevatorService = new ElevatorService();
        ExecutorService threadPool = Executors.newFixedThreadPool(7);
        elevatorService.addRequest(0,35);
        elevatorService.addRequest(5,0);
        elevatorService.addRequest(1,55);
        elevatorService.addRequest(0,10);
        elevatorService.addRequest(42,0);
        elevatorService.addRequest(0,35);
        elevatorService.addRequest(0,45);
        elevatorService.addRequest(14,0);
        waitForIt();
        assertEquals(3, elevatorService.checkAvailableElevators());
        threadPool.shutdown();
    }


    private void waitForIt() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
