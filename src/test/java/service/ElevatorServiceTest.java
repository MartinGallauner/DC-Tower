package service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatorServiceTest {

    private ElevatorService elevatorService;

    @BeforeEach
    public void beforeEach() {
        elevatorService = new ElevatorService();
    }

    @AfterEach
    public void afterEach() {
        elevatorService.shutdown();
    }

    @Test
    public void testCheckAvailableElevatorsNoRequest() {
        assertEquals(7, elevatorService.checkAvailableElevators());
    }

    @Test
    public void testElevatorServiceSingleRequest() {
        elevatorService.addRequest(0, 37);
        waitForIt();
        assertEquals(6, elevatorService.checkAvailableElevators());
    }

    @Test
    public void testElevatorServiceSmallRequest() {
        elevatorService.addRequest(5, 35);
        elevatorService.addRequest(35, 0);
        waitForIt();
        assertEquals(5, elevatorService.checkAvailableElevators());
    }

    @Test
    public void testElevatorServiceBigRequest() {
        elevatorService.addRequest(0, 35);
        elevatorService.addRequest(5, 0);
        elevatorService.addRequest(1, 55);
        elevatorService.addRequest(0, 10);
        elevatorService.addRequest(42, 0);
        elevatorService.addRequest(0, 35);
        elevatorService.addRequest(0, 45);
        elevatorService.addRequest(14, 0);
        elevatorService.addRequest(40, 0);
        waitForIt();
        assertEquals(0, elevatorService.checkAvailableElevators());
    }

    private void waitForIt() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
