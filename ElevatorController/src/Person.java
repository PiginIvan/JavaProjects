import java.util.Random;
// Класс, характеризующий человека и его запрос на вызов лифта
public class Person {
    // Переменные, отвечающие за начальный и конечный этаж вызова
    private final int startFloor, finishFloor;

    // Контруктор класса
    public Person(int numberOfFloors) {
        Random rand = new Random();
        int start = rand.nextInt(1, numberOfFloors);
        int finish = rand.nextInt(1, numberOfFloors);
        while (start == finish) {
            start = rand.nextInt(1, numberOfFloors);
        }
        startFloor = start;
        finishFloor = finish;
    }

    // Геттеры
    public int getStartFloor(){
        return startFloor;
    }

    public int getFinishFloor(){
        return finishFloor;
    }

    // Метод toString
    @Override
    public String toString() {
        return "New person from: " + startFloor + " to: " + finishFloor;
    }
}