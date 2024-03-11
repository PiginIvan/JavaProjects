import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пожалуйста, введите название входного файла:");
        Scanner sc = new Scanner(System.in);
        File inputFile = new File(sc.next());
        try {
            FrequencyCounter counter = new FrequencyCounter(inputFile);
            counter.countLetters();
            System.out.println("Пожалуйста, введите название выходного файла:");
            File outputFile = new File(sc.next());
            counter.writeFrequencyToFile(outputFile);
        }
        catch (FileNotFoundException FileErr) {
            System.out.println(FileErr.getMessage());
        }
        catch (IOException FileErr) {
            System.out.println("Произошла ошибка во время чтения или записи файла");
        }
    }
}
