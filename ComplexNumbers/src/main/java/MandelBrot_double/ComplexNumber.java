/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MandelBrot_double;

/**
 *
 * @author parke
 */
public class ComplexNumber {

    double r; // real number
    double ir; // complex number

    public ComplexNumber(double r, double c) {
        this.r = r;
        this.ir = c;
    }

    public double getReal() {
        return r;
    }

    public double getComplex() {
        return ir;
    }

    public static ComplexNumber add(ComplexNumber c1, ComplexNumber c2) {
        return new ComplexNumber(c1.getReal() + c2.getReal(), c1.getComplex() + c2.getComplex());
    }

    public ComplexNumber add(ComplexNumber c1) {
        return add(this, c1);
    }

    public static ComplexNumber subtract(ComplexNumber c1, ComplexNumber c2) {
        return new ComplexNumber(c1.getReal() - c2.getReal(), c1.getComplex() - c2.getComplex());
    }

    public ComplexNumber subtract(ComplexNumber c1) {
        return subtract(this, c1);
    }

    public static ComplexNumber multiply(ComplexNumber c1, ComplexNumber c2) {

        double tempReal = c1.getReal() * c2.getReal() - c1.getComplex() * c2.getComplex();
        double tempComplex = c1.getReal() * c2.getComplex() + c2.getReal() * c1.getComplex();

        return new ComplexNumber(tempReal, tempComplex);
    }

    public ComplexNumber multiply(ComplexNumber c1) {
        return multiply(this, c1);
    }

    public static ComplexNumber power(ComplexNumber c1, int power) { // not fully implemented now
        if (power == 1) {
            return c1;
        }
        if (power == 0) {
            return new ComplexNumber(power, power);
        }
        ComplexNumber temp = c1;

        for (int i = power - 1; i > 0; i--) {
            temp = temp.multiply(c1);
        }
        return temp;
    }

    public ComplexNumber power(int power) {
        return power(this, power);
    }

    public double radius() {
        return Math.sqrt(r * r + ir * ir);
    }

    @Override
    public String toString() {
        return "Real:" + r + " Complex:" + ir;
    }
}
