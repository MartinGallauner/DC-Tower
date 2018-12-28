package model;

import java.util.concurrent.TimeUnit;

/**
 * Provides a method to simulate the transit time of the elevator
 */

class Transit {

    private Transit() {
    }

    public static void oneSecond() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
