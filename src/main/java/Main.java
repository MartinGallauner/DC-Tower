import service.ElevatorService;

public class Main {


    public static void main(String[] args) {
        ElevatorService elevatorService = new ElevatorService();
        System.out.println(elevatorService.checkAvailableElevators());
        elevatorService.addRequest(0, 55);
        elevatorService.addRequest(1, 55);
        elevatorService.addRequest(54, 0);
        System.out.println(elevatorService.checkAvailableElevators());
    }


}
