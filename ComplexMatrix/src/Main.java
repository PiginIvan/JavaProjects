import java.util.Random;
public class Main {
    public static void main(String[] args) {
        demonstrateNumbers();
        System.out.println();
        demonstrateMatrices();
    }

    private static void demonstrateNumbers() {
        ComplexNumber num1 = new ComplexNumber(4, 7);
        ComplexNumber num2 = new ComplexNumber(2, 5);
        double var = 5.5;
        System.out.println("Модуль комплексного числа: " + num1 + " = " + num1.getModulus());
        System.out.println("Сложение чисел: " + num1 + " + " + num2 + " = " + num1.addNumbers(num2));
        System.out.println("Вычитание чисел: " + num1 + " - " + num2 + " = " + num1.subNumbers(num2));
        System.out.println("Умножение числа на константу: " + num1 + " * " + var + " = " + num1.mulNumberByVar(var));
        System.out.println("Умножение чисел: " + num1 + " * " + num2 + " = " + num1.mulNumbers(num2));
        System.out.println("Деление чисел: " + num1 + " / " + num2 + " = " + num1.divNumbers(num2));
    }

    private static void demonstrateMatrices() {
        Random gen = new Random();
        int size = 2;
        double var = 5.5;
        ComplexMatrix mat1 = new ComplexMatrix(size, size);
        ComplexMatrix mat2 = new ComplexMatrix(size, size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mat1.setElement(i, j, new ComplexNumber(gen.nextInt() % 15, gen.nextInt() % 15));
                mat2.setElement(i, j, new ComplexNumber(gen.nextInt() % 15, gen.nextInt() % 15));
            }
        }
        System.out.println("Первая матрица");
        System.out.print(mat1);
        System.out.println("Вторая матрица");
        System.out.print(mat2);
        System.out.println("Сложение матриц");
        System.out.print(mat1.addMatrices(mat2));
        System.out.println("Вычитание матриц");
        System.out.print(mat1.subMatrices(mat2));
        System.out.println("Умножение первой матрицы на константу " + var);
        System.out.print(mat1.mulMatrixByVar(var));
        System.out.println("Умножение матриц");
        System.out.print(mat1.mulMatrices(mat2));
        System.out.println("Транспонирование первой матрицы");
        System.out.print(mat1.transposeMatrix());
        System.out.println("Детерминант первой матрицы");
        System.out.println(mat1.getDeterminant());
    }

}
