public class ComplexMatrix {
    private ComplexNumber[][] matrix; // переменная, отвечающая за двумерный массив, то есть за матрицу
    private final int rows, columns; // переменные, отвечающие за размеры матрицы, количество строк и столбцов

    ComplexMatrix() {
        rows = 1;
        columns = 1;
        matrix = new ComplexNumber[rows][columns];
        matrix[0][0] = new ComplexNumber();
    } // конструктор по умолчанию

    ComplexMatrix(int rowNum, int colNum) {
        if(rowNum <= 0 || colNum <= 0) {
            throw new IllegalArgumentException("Sizes of matrix can't be less than one");
        }
        rows = rowNum;
        columns = colNum;
        matrix = new ComplexNumber[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = new ComplexNumber();
            }
        }
    } // двухпараметрический конструктор, принимающий размеры матрицы

    ComplexMatrix(ComplexNumber[][] other) {
        rows = other.length;
        columns = other[0].length;
        matrix = other.clone();
    } // однопараметрический конструктор, принимающий двумерный массив

    ComplexMatrix(ComplexMatrix other) {
        rows = other.rows;
        columns = other.columns;
        matrix = new ComplexNumber[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.matrix[i][j] = new ComplexNumber(other.matrix[i][j]);
            }
        }
    } // конструктор копирования

    public int getRows() {
        return rows;
    } // геттер для количества строк

    public int getColumns() {
        return columns;
    } // геттер для количества столбцов

    public ComplexNumber getElement(int row, int col) {
        if(row < 0 || row >= rows || col < 0 || col >= columns) {
            throw new IllegalArgumentException("Indices go beyond matrix boundaries");
        }
        return matrix[row][col];
    } // геттер для элемента по индексам

    public void setElement(int row, int col, ComplexNumber num) {
        if(row < 0 || row >= rows || col < 0 || col >= columns) {
            throw new IllegalArgumentException("Indices go beyond matrix boundaries");
        }
        matrix[row][col] = num;
    } // сеттер для элемента по индексам

    public ComplexMatrix addMatrices(ComplexMatrix other) {
        if(rows != other.rows || columns != other.columns) {
            throw new IllegalArgumentException("Sizes of matrices must be equal");
        }
        ComplexMatrix ans = new ComplexMatrix(rows, columns);
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                ans.matrix[i][j] = new ComplexNumber(matrix[i][j].addNumbers(other.matrix[i][j]));
            }
        }
        return ans;
    } // сложение матриц

    public ComplexMatrix subMatrices(ComplexMatrix other) {
        if(rows != other.rows || columns != other.columns) {
            throw new IllegalArgumentException("Sizes of matrices must be equal");
        }
        ComplexMatrix ans = new ComplexMatrix(rows, columns);
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                ans.matrix[i][j] = new ComplexNumber(matrix[i][j].subNumbers(other.matrix[i][j]));
            }
        }
        return ans;
    } // вычитание матриц

    private ComplexNumber[] getRow(int row) {
        ComplexNumber[] ans = new ComplexNumber[columns];
        ans = matrix[row].clone();
        return ans;
    } // получить строку для умножения матриц

    private ComplexNumber[] getColumn(int column) {
        ComplexNumber[] ans = new ComplexNumber[rows];
        for (int i = 0; i < rows; i++) {
            ans[i] = matrix[i][column];
        }
        return ans;
    } // получить столбец для умножения матриц

    public ComplexMatrix mulMatrices(ComplexMatrix other) {
        if(columns != other.rows) {
            throw new IllegalArgumentException("The number of rows of the first matrix must be equal to the number of columns of the second matrix");
        }
        ComplexMatrix ans = new ComplexMatrix(rows, other.columns);
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++){
                ComplexNumber cur = new ComplexNumber();
                ComplexNumber[] curRow = this.getRow(i);
                ComplexNumber[] curCol = other.getColumn(j);
                for(int k = 0; k < curRow.length; k++) {
                    cur = cur.addNumbers(curRow[k].mulNumbers(curCol[k]));
                }
                ans.matrix[i][j] = cur;
            }
        }
        return ans;
    } // умножение матриц

    public ComplexMatrix mulMatrixByVar(double num) {
        ComplexMatrix ans = new ComplexMatrix(rows, columns);
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++){
                ans.matrix[i][j] = matrix[i][j].mulNumberByVar(num);
            }
        }
        return ans;
    } // умножение матрциы на константу

    public ComplexMatrix transposeMatrix() {
        ComplexMatrix ans = new ComplexMatrix(columns, rows);
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                ans.matrix[j][i] = matrix[i][j];
            }
        }
        return ans;
    } // транспонирование матрицы

    private ComplexNumber findMinors(ComplexNumber[][] curMatrix, int size) {
        if(size == 2){
            return (curMatrix[0][0].mulNumbers(curMatrix[1][1]).subNumbers((curMatrix[0][1].mulNumbers(curMatrix[1][0]))));
        }
        ComplexNumber ans = new ComplexNumber();
        for(int i = 0; i < size; i++) {
            ComplexNumber[][] nextMatrix = new ComplexNumber[size - 1][size - 1];
            for(int j = 1; j < size; j++) {
                int temp = 0;
                for(int k = 0; k < size; k++) {
                    if(k != i) {
                        nextMatrix[j - 1][temp++] = curMatrix[j][k];
                    }
                }
            }
            ans = ans.addNumbers(curMatrix[0][i].mulNumberByVar(Math.pow(-1, i +2)).mulNumbers(findMinors(nextMatrix, size - 1)));
        }
        return ans;
    } // рекурсивный подсчет детерминанта для каждого минора

    public ComplexNumber getDeterminant() {
        if(rows != columns){
            throw new IllegalArgumentException("Determinant can't be find for non-square matrices");
        }
        if(getRows() == 1){
            return matrix[0][0];
        }
        return findMinors(matrix, getRows());
    } // вычисление детерминанта матрицы

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(matrix[i][j].getComplex() < 0) {
                    str.append(matrix[i][j].getReal()).append("").append(matrix[i][j].getComplex()).append("i ");
                }
                else {
                    str.append(matrix[i][j].getReal()).append("+").append(matrix[i][j].getComplex()).append("i ");
                }
            }
            str.append("\n");
        }
        return str.toString();
    } // получение матрицы в виде строки для вывода в консоль

}
