public class System {


    /*
    TODO: Some collection of elevators
     */


    // Methods


    public static void main(String[] args) {


    }


    public void addRequest(int currentFloor, int destinationFloor, boolean movesDown) {

        // Search for the most efficient elevator

        // Send Elevator to currentFloor

        // Send Elevator to destinationFloor



    }


    public int checkAvailableElevators() {
        return 0;
    }

    private boolean checkIfDirectionIsDown(int currentFloor, int destinationFloor) {
        if(destinationFloor < currentFloor) {
            return true;
        }
        return false;
    }

    // TODO: The search algorithm is the core subject of this coding challenge
    private void searchBestElevator(int currentFloor) {
        // Check if an elevator is standing in this floor

        // Check if an elevator is moving towards this floor, if more than 1 - which is first?

        // Check which elevators are without Job, if more than 1 - which is closest?

        // Which elevator is the next without job?
    }



}
