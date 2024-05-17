// Класс, выступающий в качестве потока для управления системой лифтов
public class ElevatorController implements Runnable {
    // Переменные, отвечающие за количество лифтов, количество этажей, вместимость лифта, количество вызовов
    private final int numberOfElevators, numberOfFloors, elevatorSize, countOfCalls;
    // Массив, хранящий в себе лифты
    private final Elevator[] elevators;
    // Массив, хранящий в себе потоки лифтов
    private final Thread[] elevatorThreads;
    // Переменная, отвечающая за количество выполненных вызовов
    public static int countOfDeliveredPersons = 0;

    // Конструктор класса
    ElevatorController(int countOfElevators, int floors, int capacity, int calls) {
        long systemSpeed = 700;
        numberOfElevators = countOfElevators;
        numberOfFloors = floors;
        elevatorSize = capacity;
        countOfCalls = calls;
        elevators = new Elevator[numberOfElevators];
        elevatorThreads = new Thread[numberOfElevators];
        for (int i = 0; i < numberOfElevators; i++) {
            elevators[i] = new Elevator(i + 1, elevatorSize, systemSpeed);
            elevatorThreads[i] = new Thread(elevators[i]);
        }
    }

    // Метод run(), определяющий функционал потока
    public void run()  {
        for (Thread t : elevatorThreads) {
            t.start();
        }
        while(ElevatorController.countOfDeliveredPersons != countOfCalls) {
            System.out.println("Count of delivered persons: " + ElevatorController.countOfDeliveredPersons);
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException error) {
                System.out.print(error.getMessage());
                return;
            }
            System.out.println(this);
        }
        System.out.println("Count of delivered persons: " + ElevatorController.countOfDeliveredPersons);
        for(Elevator elev : elevators) {
            elev.setStoppedPosition();
        }
        System.out.println(this);
        for (Elevator elev : elevators) {
            elev.stop();
        }
    }

    // Геттер
    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    // Метод для определения запроса к конкретному лифту
    public synchronized void addNewPerson(Person newPerson) {
        float maxDistance = findBestElevator(newPerson, elevators[0]);
        int bestElevatorIndex = 0;
        for (int i = 1; i < numberOfElevators; i++) {
            float curDistance = findBestElevator(newPerson, elevators[i]);
            if (curDistance > maxDistance){
                maxDistance = curDistance;
                bestElevatorIndex = i;
            }
        }
        elevators[bestElevatorIndex].addFreePerson(newPerson);
        System.out.println(newPerson + " was defined to: " + elevators[bestElevatorIndex]);
        System.out.println();
    }

    // Метод для поиска наиболее подходящего лифта
    public float findBestElevator(Person newPerson, Elevator elev) {
        float curDiff = 0;
        int distanceDiff= numberOfFloors - Math.abs(newPerson.getStartFloor() - elev.getCurFloor());
        int directionDiff = getDirectionDiff(newPerson, elev);
        int capacityDiff = elevatorSize - elev.getCurSize();
        int elevatorDiff = -1 * elev.getFreePersonsSize();
        curDiff = distanceDiff + distanceDiff * directionDiff + capacityDiff + elevatorDiff;
        return curDiff;
    }

    // Метод для определения разницы в направлении лифта и запроса
    private static int getDirectionDiff(Person newPerson, Elevator elev) {
        TrafficSide curDir = elev.getDirection();
        int directionDiff = 0;
        if (curDir == TrafficSide.UP) {
            if (elev.getCurFloor() <= newPerson.getStartFloor()) {
                directionDiff = 1;
            }
            else {
                directionDiff = -1;
            }
        }
        else if (curDir == TrafficSide.DOWN) {
            if (elev.getCurFloor() >= newPerson.getStartFloor()) {
                directionDiff = 1;
            }
            else {
                directionDiff = -1;
            }
        }
        return directionDiff;
    }

    // Метод toString
    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        message.append("Current state of all elevators:").append('\n');
        for (Elevator elev : elevators){
            message.append(elev.toString()).append('\n');
        }
        return message.toString();
    }

}