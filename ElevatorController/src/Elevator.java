import java.util.Vector;
// Класс, характеризующий лифт
public class Elevator implements Runnable {
    // Переменные, отвечающие за вместимость лифта и его id
    private final int elevatorSize, id;
    // Переменная, отвечающая за скорость лифта
    private final long speedOfElevator;
    // Переменные, отвечающие за текущий этаж лифта, текущую заполненность и этаж, на который он едет в данный момент
    private int curFloor = 0, curSize = 0, finishFloor = 0;
    // Переменные, отвечающие за наличие цели у лифта, нахождения в движении и его работоспособности
    private boolean hasTarget = false, isMoving = false, isRunning = true;
    // Переменные, отвечающие за Vector из Person, которым соответствуют свободные люди, которые определены к данному лифту и те, кто находятся в нем в данный момент
    private Vector<Person> freePersons, bookedPersons;

    // Конструктор класса
    public Elevator(int ID, int size, long speed) {
        id = ID;
        elevatorSize = size;
        speedOfElevator = speed;
        freePersons = new Vector<>(elevatorSize);
        bookedPersons = new Vector<>(elevatorSize);
    }

    // Метод run(), определяющий функционал потока
    public void run() {
        while(isRunning) {
            if (freePersons.isEmpty() && bookedPersons.isEmpty()) {
                finishFloor = curFloor;
                hasTarget = false;
                continue;
            }
            deliverPerson();
            takePerson();
            if (finishFloor == curFloor) {
                hasTarget = false;
            }
            if (freePersons.isEmpty() && bookedPersons.isEmpty()) {
                finishFloor = curFloor;
                hasTarget = false;
                continue;
            }
            if (!hasTarget){
                if(!bookedPersons.isEmpty()) {
                    finishFloor = bookedPersons.getFirst().getFinishFloor();
                }
                else {
                    finishFloor = freePersons.getFirst().getStartFloor();
                }
                hasTarget = true;
            }
            isMoving = true;
            try {
                Thread.sleep(speedOfElevator);
            }
            catch (InterruptedException error) {
                System.out.print(error.getMessage());
            }
            curFloor += (curFloor - finishFloor) > 0 ? -1 : 1;
            isMoving = false;
        }
    }

    // Метод stop(), отключающий работоспособность объекта
    public void stop() {
        isRunning = false;
    }

    // Геттеры
    public int getCurFloor() {
        return curFloor;
    }

    public int getCurSize() {
        return curSize;
    }

    public TrafficSide getDirection() {
        if (curFloor > finishFloor){
            return TrafficSide.DOWN;
        }
        else if (curFloor < finishFloor) {
            return TrafficSide.UP;
        }
        else {
            return TrafficSide.STAND;
        }
    }

    public int getFreePersonsSize() {
        return freePersons.size();
    }

    // Метод для добавления человека в Vector свободных людей
    public synchronized void addFreePerson(Person newPerson) {
        freePersons.add(newPerson);
    }

    // Метод для добавления человека в Vector забранных людей
    public void addBookedPerson(int index) {
        Person newPerson = freePersons.get(index);
        if (newPerson.getFinishFloor() != curFloor){
            bookedPersons.add(newPerson);
            curSize++;
        }
    }

    // Метод для удаления людей из Vector забранных
    public void removeBookedPerson(Integer[] indexes) {
        Vector<Person> remainingPersons = new Vector<>(bookedPersons.size() - indexes.length);
        for(int i = 0; i < bookedPersons.size(); i++) {
            boolean isRemoving = false;
            for (Integer index : indexes) {
                if (i == index) {
                    isRemoving = true;
                    break;
                }
            }
            if (!isRemoving) {
                remainingPersons.add(bookedPersons.get(i));
            }
        }
        int oldSize = bookedPersons.size();
        bookedPersons = remainingPersons;
        ElevatorController.countOfDeliveredPersons += oldSize - bookedPersons.size();
        curSize = oldSize - bookedPersons.size();
    }

    // Метод для удаления людей из Vector свободных
    public void removeFreePersons(Integer[] indexes) {
        if(freePersons.size() <= indexes.length) {
            return;
        }
        Vector<Person> remainingPersons = new Vector<>(freePersons.size() - indexes.length);
        for(int i = 0; i < freePersons.size(); i++) {
            boolean isRemoving = false;
            for (Integer index : indexes) {
                if (i == index) {
                    isRemoving = true;
                    break;
                }
            }
            if (!isRemoving) {
                remainingPersons.add(freePersons.get(i));
            }
        }
        freePersons = remainingPersons;
    }

    // Метод, отвечающий за доставку человека на нужный этаж
    public void deliverPerson() {
        Vector<Integer> deliveredPersons = new Vector<>(curSize);
        for (int i = 0; i < bookedPersons.size(); i++) {
            if (curFloor == bookedPersons.get(i).getFinishFloor()) {
                deliveredPersons.add(i);
            }
        }
        removeBookedPerson((Integer[]) deliveredPersons.toArray(new Integer[0]));
    }

    // Метод, отвечающий за вхождения человека в лифт
    public void takePerson() {
        Vector<Integer> newPassengers = new Vector<>(freePersons.size());
        for (int i = 0; i < freePersons.size(); i++) {
            if (curFloor == freePersons.get(i).getStartFloor()) {
                newPassengers.add(i);
            }
        }
        Vector<Integer> removeCandidates = new Vector<>(newPassengers.size());
        for (int i : newPassengers) {
            if (curSize >= elevatorSize) {
                break;
            }
            addBookedPerson(i);
            removeCandidates.add(i);
        }
        removeFreePersons((Integer[]) removeCandidates.toArray(new Integer[0]));
    }

    // Метод, отвечающий за установку конечного положения лифта
    public void setStoppedPosition() {
        curSize = 0;
        hasTarget = false;
        isMoving = false;
        isRunning = false;
    }

    // Метод toString
    @Override
    public String toString() {
        return "Elevator " + id + " state: " + " curFloor: " + curFloor + ", curSize: " + curSize + ", finishFloor: " + finishFloor + ", hasTarget: " + hasTarget + ", isMoving: " + isMoving;
    }
}