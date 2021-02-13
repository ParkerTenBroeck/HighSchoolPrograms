/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author parke
 */
public class ComplexNumbers {

    final static int width = 400;
    final static int height = 300;
    
   
    final static public MathContext MC = new MathContext(100, RoundingMode.UP);
    
    

    static BigDecimal bbWidth = new BigDecimal(2, MC) ;
    static BigDecimal bbHeight = new BigDecimal(2, MC);
    static BigDecimal bbCenterX = new BigDecimal("-0.5622025180044592", MC);
    static BigDecimal bbCenterY = new BigDecimal("0.6428181119851231", MC);

    final static int maxItterations = 50;

    static BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    public static void main(String[] args) {

        Frame frmae = new Frame(width, height);

        //while (true) {

            bbWidth = bbWidth.divide(new BigDecimal(1), MC);
            bbWidth = bbWidth.divide(new BigDecimal(1), MC);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    BigDecimal xPixel = bbWidth.multiply(new BigDecimal((x / (double) width - 0.5) * 2.0, MC), MC).add(bbCenterX, MC);
                    BigDecimal yPixel = bbWidth.multiply(new BigDecimal((y / (double) height - 0.5) * 2.0, MC), MC).add(bbCenterY, MC);

                    int i = calculatePixel(xPixel, yPixel);
                    //System.out.println(System.currentTimeMillis() - temp);
                    //System.out.println(i);
                    //System.out.println(i);
                    if (i > maxItterations) {
                        image.setRGB(x, y, 0);
                    } else {
                        image.setRGB(x, y, Color.getHSBColor(i / (float) maxItterations, 1, 1).getRGB());
                    }

                }
            }

            frmae.updateDisplay(image);
            System.out.println("done");
        //}
    }

    public static int calculatePixel(BigDecimal xPos, BigDecimal yPos) {
        int itterations = 0;

        ComplexNumber c = new ComplexNumber(xPos, yPos);
        ComplexNumber z = new ComplexNumber(0, 0);

        while (z.radius().doubleValue() <= 4 && itterations <= maxItterations) {
            z = ComplexNumber.add(ComplexNumber.power(z, 2), c);
            //System.out.println(z);
            itterations++;
        }
        //System.out.println(itterations);

        return itterations;
    }

}
