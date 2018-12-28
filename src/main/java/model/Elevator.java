package model;

import lombok.Data;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Encapsulates the state of an Elevator, e.g. the current floor and actions, e.g. moving.
 */
@ThreadSafe
@Data
public class Elevator {

    private final int id;
    private AtomicInteger currentFloor;
    private AtomicBoolean isAvailable;

    public Elevator(int id) {
        currentFloor = new AtomicInteger(0);
        isAvailable = new AtomicBoolean(true);
        this.id = id;
    }

    public void moveUp() {
        Transit.oneSecond();
        // condition top floor is enforced outside this class
        currentFloor.incrementAndGet();
    }

    public void moveDown() {
        Transit.oneSecond();
        // condition lowest floor is enforced outside this class
        currentFloor.decrementAndGet();
    }

    public int getCurrentFloor() {
        return currentFloor.get();
    }

    public boolean isAvailable() {
        return isAvailable.get();
    }

    public void setAvailable(boolean available) {
        isAvailable.set(available);
    }

}
