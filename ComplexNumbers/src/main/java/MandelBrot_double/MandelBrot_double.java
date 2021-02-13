/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MandelBrot_double;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author parke
 */
public class MandelBrot_double {

    final static int width = 1000;
    final static int height = 1000;

    static final Frame frame = new Frame(width, height);

    private static double bbScale = 1;
    private static double bbCenterX = -0.12814951569245261;
    private static double bbCenterY = 0.9863224202683802;

    static int maxItterations = 170;

    static volatile BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    public static void main(String[] args) {

        bbCenterX = 0;//-0.5;
        bbCenterY = 0;
        //while (true) {
        //while(true){
        //bbScale /= 1.3;
        calculateScreen(null);
        //}
        //}
    }

    public static double[] getBoundingBox() {
        return new double[]{bbScale, bbCenterX, bbCenterY};
    }

    public static void setBoundingBox(double[] boundingBox) {
        bbScale = boundingBox[0];
        bbCenterX = boundingBox[1];
        bbCenterY = boundingBox[2];
    }

    public synchronized static void calculateScreen(ComplexNumber constant) {

        maxItterations = (int) (50 * Math.pow(Math.log10(width / bbScale), 1.25));//kinda random but is optimal
        int cores = Runtime.getRuntime().availableProcessors();

        Thread[] threads = new Thread[cores];

        double rowsPerThread = height / (double) threads.length;

        for (int i = 0; i < cores; i++) {
            threads[i] = createRenderingThread((int) (i * rowsPerThread), (int) ((i + 1) * rowsPerThread), constant);
            threads[i].start();
        }

        boolean alive = true;
        while (alive) {
            try {
                Thread.sleep(10);
                alive = false;
                for (int i = 0; i < threads.length; i++) {
                    alive = alive || threads[i].isAlive();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(MandelBrot_double.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        frame.updateDisplay(image);
    }

    public static Thread createRenderingThread(int yStart, int yEnd, ComplexNumber constant) {
        Thread haha = new Thread((new Runnable() {
            int yStart;
            int yEnd;

            public void run() {
                for (int y = yStart; y < yEnd; y++) {
                    for (int x = 0; x < width; x++) {

                        int i = calculatePixel(((x / (double) width) - 0.5) * bbScale * 2.0 + bbCenterX, ((y / (double) height) - 0.5) * bbScale * 2.0 + bbCenterY, constant);
                        //System.out.println(i);
                        if (i == 1) {
                            image.setRGB(x, y, 0);
                        } else if (i > maxItterations) {
                            image.setRGB(x, y, 0);
                        } else {
                            image.setRGB(x, y, Color.getHSBColor(i / (float) maxItterations, 1, 1).getRGB());
                        }

                    }
                }
            }

            public Runnable pass(int yStart, int yEnd) {
                this.yStart = yStart;
                this.yEnd = yEnd;
                return this;
            }
        }).pass(yStart, yEnd));
        return haha;
    }

    public static int calculatePixel(double xPos, double yPos, ComplexNumber constant) {
        int itterations = 0;
        ComplexNumber c;
        ComplexNumber z;

        if (constant == null) {
            c = new ComplexNumber(xPos, yPos);
            z = new ComplexNumber(0, 0);
        } else {
            z = new ComplexNumber(xPos, yPos);
            c = constant;
        }

        while (z.radius() <= 2 && itterations <= maxItterations) {
            //System.err.println(z);
            z = z.power(2).add(c);

            itterations++;
        }
        //System.out.println(itterations);

        return itterations;
    }

}
