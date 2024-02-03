public class ComplexNumber {
    private double real, complex; // переменные, отвечающие за действительную и мнимую часть комплексного числа

    ComplexNumber() {
        real = 0;
        complex = 0;
    } // конструктор по умолчанию

    ComplexNumber(double realPart, double complexPart) {
        real = realPart;
        complex = complexPart;
    } // двухпараметрический конструктор

    ComplexNumber(ComplexNumber other) {
        real = other.real;
        complex = other.complex;
    } // конструктор копирования

    public double getReal() {
        return real;
    } // геттер для действительной части

    public double getComplex() {
        return complex;
    } // геттер для мнимой части

    public void setReal(double realPart) {
        real = realPart;
    } // сеттер для действительной части

    public void setComplex(double complexPart) {
        complex = complexPart;
    } // сеттер для мнимой части

    public ComplexNumber conjugateNumber(ComplexNumber num) {
        return new ComplexNumber(num.real, -num.complex);
    } // получение комплексно-сопряженного числа

    public double getModulus() {
        return Math.sqrt(real * real + complex * complex);
    } // получение модуля комплексного числа

    public ComplexNumber addNumbers(ComplexNumber other) {
        return new ComplexNumber(this.real + other.real, this.complex + other.complex);
    } // сложение комплексных чисел

    public ComplexNumber subNumbers(ComplexNumber other) {
        return new ComplexNumber(this.real - other.real, this.complex - other.complex);
    } // вычитание комплексных чисел

    public ComplexNumber mulNumbers(ComplexNumber other) {
        return new ComplexNumber(this.real * other.real - this.complex * other.complex, this.real * other.complex + other.real * this.complex);
    } // умножение комплексных чисел

    public ComplexNumber mulNumberByVar(double num) {
        return new ComplexNumber(this.real * num, this.complex * num);
    } // умножение комплексного числа на константу

    public ComplexNumber divNumbers(ComplexNumber other) {
        if (other.real == 0 && other.complex == 0) {
            throw new ArithmeticException("Divider can't be equal to zero");
        }
        ComplexNumber conj = conjugateNumber(other);
        double denominator = (other.real * conj.real - other.complex * conj.complex);
        return new ComplexNumber((this.real * conj.real - this.complex * conj.complex) / denominator, (this.real * conj.complex + this.complex * conj.real) / denominator);
    } // деление комплексных чисел

    @Override
    public String toString() {
        if(complex < 0) return real + "" + complex + "i";
        return real + "+" + complex + "i";
    } // получение комплексного числа в виде строки для вывода в консоль

}
