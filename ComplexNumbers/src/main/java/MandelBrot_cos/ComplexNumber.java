/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MandelBrot_cos;

import MandelBrot_double.*;

/**
 *
 * @author parke
 */
public class ComplexNumber {

    Quad r; // real number
    Quad ir; // complex number

    public ComplexNumber(Quad r, Quad c) {
        this.r = r;
        this.ir = c;
    }

    public Quad getReal() {
        return r;
    }

    public Quad getComplex() {
        return ir;
    }

    public static ComplexNumber add(ComplexNumber c1, ComplexNumber c2) {
        return new ComplexNumber(Quad.add(c1.getReal(), c2.getReal()), Quad.add(c1.getComplex(), c2.getComplex()));
    }

    public static ComplexNumber subtract(ComplexNumber c1, ComplexNumber c2) {
        return new ComplexNumber(Quad.subtract(c1.getReal(), c2.getReal()), Quad.subtract(c1.getComplex(), c2.getComplex()));
    }

    public static ComplexNumber multiply(ComplexNumber c1, ComplexNumber c2) {
        
        Quad tempReal = Quad.subtract(Quad.multiply(c1.getReal(), c2.getReal()), Quad.multiply(c1.getComplex(), c2.getComplex()));
        
        System.out.println(Quad.multiply(c1.getReal(), c2.getReal()).getDoubleRep());
        
        Quad tempComplex = Quad.add(Quad.multiply(c1.getReal(), c2.getComplex()), Quad.multiply(c2.getReal(), c1.getComplex()));

        return new ComplexNumber(tempReal, tempComplex);
    }

    public static ComplexNumber power(ComplexNumber c1, int power) { // not fully implemented (duh)
        return multiply(c1, c1);
    }

    public Quad radius() {
        return Quad.add(Quad.multiply(r, r), Quad.multiply(ir, ir));
    }

    @Override
    public String toString() {
        return "Real:" + r.getDoubleRep() + " Complex:" + ir.getDoubleRep();
    }
}
