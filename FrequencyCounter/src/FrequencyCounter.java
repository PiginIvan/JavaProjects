import java.io.*;
import java.util.HashMap;

public class FrequencyCounter {
    private final HashMap<Character, Integer> lettersMap; // HashMap для хранения количества символом английского алфавита
    private final File inputFile; // Название входного файла

    FrequencyCounter(File curInputFile) throws FileNotFoundException {
        if(!curInputFile.exists()) {
            throw new FileNotFoundException("Файл " + curInputFile + " не существует");
        }
        if(!curInputFile.isFile()) {
            throw new FileNotFoundException(curInputFile + " не является файлом");
        }
        if(!curInputFile.canRead()) {
            throw new FileNotFoundException("Файл " + curInputFile + " не может быть прочитан");
        }
        inputFile = curInputFile;
        lettersMap = new HashMap<>();
    } // Конструктор, принимающий имя входного файла

    public void countLetters() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile.getName()))) {
            String curString = br.readLine();
            while (curString != null) {
                for(int i = 0; i < curString.length(); i++) {
                    char curSymbol = curString.charAt(i);
                    if(curSymbol >= 'a' && curSymbol <= 'z' || curSymbol >= 'A' && curSymbol <= 'Z') {
                        if (lettersMap.containsKey(curSymbol)) {
                            lettersMap.put(curSymbol, lettersMap.get(curSymbol) + 1);
                        }
                        else {
                            lettersMap.put(curSymbol, 1);
                        }
                    }
                }
                curString = br.readLine();
            }
        }
    } // Метод для подсчета букв английского алфавита и сохранения данных в hashmap

    public void writeFrequencyToFile(File curOutputFile) throws IOException {
        try (PrintWriter wr = new PrintWriter(curOutputFile)) {
            wr.print(lettersMap);
        }
    } // Метод для записи итоговых данных счетчика в выходной файл
}
