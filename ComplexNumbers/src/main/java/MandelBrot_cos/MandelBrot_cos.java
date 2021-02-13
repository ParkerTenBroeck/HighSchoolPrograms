/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MandelBrot_cos;

import MandelBrot_double.*;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author parke
 */
public class MandelBrot_cos {

    final static int width = 200;
    final static int height = 200;

    static Quad bbScale = new Quad(2);
    static Quad bbCenterX = new Quad(-0.12814951569245261);
    static Quad bbCenterY = new Quad(0.9863224202683802);

    static int maxItterations = 70;

    static BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    public static void main(String[] args) {

//        ComplexNumber c1 = new ComplexNumber(new Quad(1), new Quad(2));
//        ComplexNumber c2 = new ComplexNumber(new Quad(3), new Quad(4));
//        
//        System.out.println(ComplexNumber.multiply(c1, c2));
ComplexNumber temp = new ComplexNumber(new Quad(0.3), new Quad(-1));
System.out.println(ComplexNumber.multiply(temp, temp));

//System.exit(0);

System.out.println(calculatePixel(new Quad(0.3), new Quad(-1)));
//System.exit(0);

        Frame frmae = new Frame(width, height);

        while (true) {

            //maxItterations = (int) (-815 * bbScale + 1700);
            //System.out.println(maxItterations);
            //bbScale = 1.0780050281702675E-15;
            bbScale = Quad.divide(bbScale, new Quad(1.3));

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    Quad xPos = Quad.fromFraction(x, width).subtract(new Quad(0.5)).multiply(new Quad(2));//.multiply(bbScale);//.add(bbCenterX);
                    Quad yPos = Quad.fromFraction(y, height).subtract(new Quad(0.5)).multiply(new Quad(2));//.multiply(bbScale);//.add(bbCenterY);
                    //System.out.println(xPos.toDecString(5) + " " + yPos.toDecString(5));
                    int i = calculatePixel(xPos, yPos);
                    //System.out.println(i);
                    if (i > maxItterations) {
                        image.setRGB(x, y, 0);
                    } else {
                        image.setRGB(x, y, Color.getHSBColor(i / (float) maxItterations, 1, 1).getRGB());
                    }

                }
                //System.out.println();
            }

            frmae.updateDisplay(image);
        }
    }

    public static int calculatePixel(Quad xPos, Quad yPos) {
        int itterations = 0;

        ComplexNumber c = new ComplexNumber(xPos, yPos);
        ComplexNumber z = new ComplexNumber(new Quad(0), new Quad(0));

        while (z.radius().getDoubleRep() <= 4 && itterations <= maxItterations) {
            System.err.println(z.getReal().getDoubleRep() + " " + z.getComplex().getDoubleRep());
            z = ComplexNumber.add(ComplexNumber.power(z, 2), c);
            //System.out.println(z);
            itterations++;
        }
        //System.err.println(itterations);

        return itterations;
    }

}
