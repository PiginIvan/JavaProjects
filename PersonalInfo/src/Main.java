import java.time.DateTimeException;

public class Main {
    public static void main(String[] args) {
        PersonalDataHandler processor = new PersonalDataHandler();
        processor.inputInfo();
        processor.getSex();
        try {
            processor.getAge();
            processor.printInfo();
        }
        catch (DateTimeException error) {
            System.out.println("Введена некорректная дата рождения");
        }
    }
}
