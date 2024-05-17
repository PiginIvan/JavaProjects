import java.util.Scanner;

public class Main {
    // Основной метод для запуска двух потоков
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] params = initElevatorParameters(sc);
        int numberOfElevators = params[0], numberOfFloors = params[1], sizeOfElevator = params[2], countOfCalls = params[3];

        ElevatorController newElevatorBlock = new ElevatorController(numberOfElevators, numberOfFloors, sizeOfElevator, countOfCalls);
        Thread ElevatorsThread = new Thread(newElevatorBlock);

        PersonsController newPersonBlock = new PersonsController(newElevatorBlock, countOfCalls);
        Thread PersonsThread = new Thread(newPersonBlock);

        ElevatorsThread.start();
        PersonsThread.start();
    }

    // Метод для инициализации параметров программы
    private static int[] initElevatorParameters(Scanner sc) {
        System.out.print("Input number of elevators: ");
        int numberOfElevators = sc.nextInt();
        System.out.print("Input number of floors: ");
        int numberOfFloors = sc.nextInt();
        System.out.print("Input number of elevator size: ");
        int sizeOfElevator = sc.nextInt();
        System.out.print("Input number of calls: ");
        int countOfCalls = sc.nextInt();
        return new int[]{numberOfElevators, numberOfFloors, sizeOfElevator, countOfCalls};
    }
}