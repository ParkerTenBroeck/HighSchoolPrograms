/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.math.BigDecimal;
import static test.ComplexNumbers.MC;

/**
 *
 * @author parke
 */
public class ComplexNumber {

    BigDecimal r; // real number
    BigDecimal ir; // complex number

    public ComplexNumber(BigDecimal r, BigDecimal c) {
        this.r = r;
        this.ir = c;
    }
        public ComplexNumber(double r, double c) {
        this.r = new BigDecimal(r, MC);
        this.ir = new BigDecimal(c, MC);
    }

    public BigDecimal getReal() {
        return r;
    }

    public BigDecimal getComplex() {
        return ir;
    }

    public static ComplexNumber add(ComplexNumber c1, ComplexNumber c2) {
        return new ComplexNumber(c1.getReal().add(c2.getReal(), MC), c1.getComplex().add(c2.getComplex(), MC));
    }

    public static ComplexNumber multiply(ComplexNumber c1, ComplexNumber c2) {

        BigDecimal tempReal = c1.getReal().multiply(c2.getReal(), MC).subtract(c1.getComplex().multiply(c2.getComplex(), MC), MC);
        BigDecimal tempComplex = c1.getReal().multiply(c2.getComplex(), MC).add(c2.getReal().multiply(c1.getComplex(), MC), MC);

        return new ComplexNumber(tempReal, tempComplex);
    }

    public static ComplexNumber power(ComplexNumber c1, int power) { // not fully implemented now
        return multiply(c1, c1);
    }
    
    public BigDecimal radius(){
        return r.multiply(r).add(ir.multiply(ir));
    }

    @Override
    public String toString() {
        return "Real:" + r + " Complex:" + ir;
    }
}
