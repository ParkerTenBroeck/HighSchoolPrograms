/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MandelBrot_cos;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author parke
 */
public class Quad {

    private long c; //value
    private long n; //exponent

    public Quad(long c, long n) {
        this.c = c;
        this.n = n;
    }

    public Quad(long val) {
        n = 0;
        c = val;
    }

    public static Quad fromFraction(long val1, long val2) {
        return Quad.divide(new Quad(val1), new Quad(val2));
    }

    public Quad(double val) {
        n = Math.getExponent(val) - 52;
        c = (Double.doubleToRawLongBits(val) & new Long("4503599627370495")) + new Long("4503599627370496");

        int max = 129;

        while ((c & 1) == 0 && max > 0) {
            c = c >> 1;
            n += 1;
            max--;
        }

        if (val < 0) {
            c = -c;
        }
    }

    public long getC() {
        return c;
    }

    public long getN() {
        return n;
    }

    public Quad inverse() {
        return null;
    }

    public static Quad multiply(Quad val1, Quad val2) {
        
        //System.out.println(val1.getDoubleRep());
        
        val1 = val1.maxSig();
        val2 = val2.maxSig();
        
        val1 = new Quad(val1.getC() >> 32, val1.getN() + 32);
        val2 = new Quad(val2.getC() >> 32, val2.getN() + 32);
        
        //System.out.println(val1.getDoubleRep());
        
        return new Quad((long) val1.getC() * (long) val2.getC(), val1.getN() + val2.getN());
    }

    public static Quad divide(Quad val1, Quad val2) {

        boolean neg = false;

        if (val1.isNegative()) {
            val1 = val1.flipSign();
            neg = !neg;
        }
        if (val2.isNegative()) {
            val2 = val2.flipSign();
            neg = !neg;
        }

        val1 = val1.maxSig();
        val2 = val2.minSig();

        Quad temp = new Quad((long) val1.getC() / (long) val2.getC(), val1.getN() - val2.getN());

        if (neg) {
            System.out.println(temp.getDoubleRep());
            temp = new Quad(-temp.c, temp.n);
            System.out.println(temp.minSig().getDoubleRep());
        }

        return temp.minSig();
    }

    public boolean isNegative() {
        return c < 0;
    }

    public static Quad add(Quad val1, Quad val2) {

        long ed = val1.getN() - val2.getN();

        if (ed > 0) {

            return new Quad((val1.getC() << ed) + val2.getC(), val2.getN()).minSig(); //if val2 is smaller

        } else if (ed < 0) {

            return new Quad((val2.getC() << -ed) + val1.getC(), val1.getN()).minSig(); //if val 1 is smaller

        }
        return new Quad(val1.getC() + val2.getC(), val1.getN()).minSig();//if exponents are the same
    }

    public static Quad subtract(Quad val1, Quad val2) {
        return add(val1, val2.flipSign());
    }

    public Quad add(Quad val) {
        return add(this, val);
    }

    public Quad subtract(Quad val) {
        return subtract(this, val);
    }

    public Quad multiply(Quad val) {
        return multiply(this, val);
    }

    public Quad divide(Quad val) {
        return divide(this, val);

    }

    public Quad flipSign() {
        return new Quad(-c, n);
    }

    public double getDoubleRep() {
        return c * Math.pow(2, n);
    }

    public Quad minSig() {

        int max = 129;
        long tempC = this.c;
        long tempN = this.n;

        while ((tempC & 1) == 0 && max > 0) {
            tempC = tempC >> 1;
            tempN += 1;
            max--;
        }
        return new Quad(tempC, tempN);
    }

    public Quad maxSig() {
        int max = 129;
        long tempC = this.c;
        long tempN = this.n;

        if (tempC > 0) {
            while ((tempC >> 126) == 0 && max > 0) {
                tempC = tempC << 1;
                tempN += -1;
                max--;
            }
        } else if (tempC < 0) {
            while ((tempC >> 126) == 0 && max > 0) {
                tempC = tempC << 1;
                tempN += -1;
                max--;
            }
        } else {
            return new Quad(0, 0);
        }
        return new Quad(tempC, tempN);
    }

    @Override
    public String toString() {
        return c + "*2^" + n;
    }

    public String toDecString(int decimalPlaces) {
        MathContext mc = new MathContext(decimalPlaces, RoundingMode.UP);
        return new BigDecimal(c, mc).multiply(new BigDecimal(2, mc).pow((int) n, mc), mc).toString();
    }
}
