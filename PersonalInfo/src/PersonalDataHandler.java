import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class PersonalDataHandler {
    private String surname, name, patronymic, sex, agePrefix; // поля для фамилии, имени, отчества, пола, префикса возраста
    private int age; // поле для позраста
    private String[] dates; // поле с массивом строк для дат рождения

    public PersonalDataHandler() {
        surname = "";
        name = "";
        patronymic = "";
        sex = "";
        age = 0;
        dates = new String[3];
    } // конструктор по умолчанию

    public void inputInfo() {
        System.out.println("Пожалуйста, введите свою фамилию:");
        Scanner sc = new Scanner(System.in);
        surname = sc.next();
        System.out.println("Пожалуйста, введите своё имя:");
        name = sc.next();
        System.out.println("Пожалуйста, введите своё отчество:");
        patronymic = sc.next();
        System.out.println("Пожалуйста, введите свою дату рождения в формате (день.месяц.год):");
        String dateOfBirth = sc.next();
        dates = dateOfBirth.split("\\.");
    } // ввод информации про человека

    public void getSex() {
        if(patronymic.charAt(patronymic.length() - 1) == 'а') {
            sex = "Женский";
        }
        else {
            sex = "Мужской";
        }
    } // определение пола человека

    public void getAge() throws DateTimeException{
        LocalDate presentDate = LocalDate.now();
        int day = Integer.parseInt(dates[0]);
        int month = Integer.parseInt(dates[1]);
        int year = Integer.parseInt(dates[2]);
        LocalDate dateOfBirth = LocalDate.of(year, month, day);
        age = Period.between(dateOfBirth, presentDate).getYears();
        if(age < 0) {
            throw new DateTimeException("Введена некорректная дата рождения");
        }
        if(age % 10 == 1 && (age / 10) % 10 != 1) {
            agePrefix = "год";
        }
        else if ((age % 10 == 2 || age % 10 == 3 || age % 10 == 4) && (age / 10) % 10 != 1){
            agePrefix = "года";
        }
        else {
            agePrefix = "лет";
        }
    } // определение возраста человека

    public void printInfo() {
        System.out.println("Фамилия и инициалы: " + surname + " " + name.charAt(0) + "." + patronymic.charAt(0) + ".");
        System.out.println("Пол: " + sex);
        System.out.println("Возраст: " + age + " " + agePrefix);
    } // печать обработанных данных про человека
}
