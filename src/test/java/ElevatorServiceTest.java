import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;



public class ElevatorServiceTest {

    @Test
    public void testCheckAvailableElevators() {
        ElevatorService elevatorServiceTest = new ElevatorService();
        assertEquals(7, elevatorServiceTest.checkAvailableElevators());
    }
}
