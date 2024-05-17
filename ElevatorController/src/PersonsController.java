// Класс, выступающий в качестве потока для генераций запросов людей на вызов лифта
public class PersonsController implements Runnable {
    // Класс, выступающий в качестве потока для управления системой лифтов
    private final ElevatorController ElevatorBlock;
    // Переменные, отвечающие за количество этажей и количество запросов
    private final int numberOfFloors, numberOfCalls;

    //Конструктор класса
    public PersonsController(ElevatorController elevatorBlock, int maxCalls) {
        ElevatorBlock = elevatorBlock;
        numberOfFloors = ElevatorBlock.getNumberOfFloors();
        numberOfCalls = maxCalls;
    }

    // Метод run(), определяющий функционал потока
    public void run() {
        long delay = 1000;
        int indexOfCall = 0;
        while(true) {
            if (numberOfCalls >= 0) {
                if (indexOfCall < numberOfCalls) {
                    indexOfCall++;
                }
                else {
                    System.out.println("All calls have been generated!");
                    return;
                }
            }
            try {
                Thread.sleep(delay);
            }
            catch (InterruptedException error) {
                System.out.print(error.getMessage());
                return;
            }
            Person newPersonCall = new Person(numberOfFloors);
            ElevatorBlock.addNewPerson(newPersonCall);
        }
    }

}