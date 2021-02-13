/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4_color_gradiant;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

/**
 *
 * @author parke
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        BufferedImage imageTrue = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
		BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        Color UL = Color.RED;
        Color UR = Color.BLUE;
        Color BL = Color.GREEN;
        Color BR = Color.YELLOW;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                image.setRGB(x, y, calcColor(UL, UR, BL, BR, x, y, image.getWidth(), image.getHeight()).getRGB());
				imageTrue.setRGB(x, y, calcColorTrue(UL, UR, BL, BR, x, y, image.getWidth(), image.getHeight()).getRGB());
            }
        }

        try {
            File outputfile = new File("saved.png");
			File outputfileTrue = new File("savedTrue.png");
            ImageIO.write(image, "png", outputfile);
			ImageIO.write(imageTrue, "png", outputfileTrue);
        } catch (Exception e) {

        }
//
//        Scanner scanner = new Scanner(System.in);
//        int n = 0;
//        while (!scanner.hasNext()) {
//        }
//        n = scanner.nextInt();
//
//        printSierpinski((int)Math.pow(2, n));
    }

    static void printSierpinski(int n) {
        for (int y = n - 1; y >= 0; y--) {

            // printing space till 
            // the value of y 
            for (int i = 0; i < y; i++) {
                System.out.print(" ");
            }

            // printing '*' 
            for (int x = 0; x + y < n; x++) {

                // printing '*' at the appropriate 
                // position is done by the and  
                // value of x and y wherever value 
                // is 0 we have printed '*' 
                if ((x & y) != 0) {
                    System.out.print(" "
                            + " ");
                } else {
                    System.out.print("* ");
                }
            }

            System.out.print("\n");
        }
    }

    public static Color calcColorTrue(Color UL, Color UR, Color BL, Color BR, double xIndex, double yIndex, double xSize, double ySize) {

        xSize -= 1;
        ySize -= 1;

        double ULP = ((xSize - xIndex) * (ySize - yIndex)) / (xSize * ySize);
        double URP = (xIndex * (ySize - yIndex)) / (xSize * ySize);
        double BLP = ((xSize - xIndex) * yIndex) / (ySize * xSize);
        double BRP = (yIndex * xIndex) / (ySize * xSize);

        int R = (int) Math.sqrt((UL.getRed() * UL.getRed() * ULP) + (UR.getRed() * UR.getRed() * URP) + (BL.getRed() * BL.getRed() * BLP) + (BR.getRed() * BR.getRed() * BRP));
        int G = (int) Math.sqrt((UL.getGreen() * UL.getGreen() * ULP) + (UR.getGreen() * UR.getGreen() * URP) + (BL.getGreen() * BL.getGreen() * BLP) + (BR.getGreen() * BR.getGreen() * BRP));
        int B = (int) Math.sqrt((UL.getBlue() * UL.getBlue() * ULP) + (UR.getBlue() * UR.getBlue() * URP) + (BL.getBlue() * BL.getBlue() * BLP) + (BR.getBlue() * BR.getBlue() * BRP));

        return new Color(R, G, B);
    }
	
	    public static Color calcColor(Color UL, Color UR, Color BL, Color BR, double xIndex, double yIndex, double xSize, double ySize) {

        xSize -= 1;
        ySize -= 1;

        double ULP = ((xSize - xIndex) * (ySize - yIndex)) / (xSize * ySize);
        double URP = (xIndex * (ySize - yIndex)) / (xSize * ySize);
        double BLP = ((xSize - xIndex) * yIndex) / (ySize * xSize);
        double BRP = (yIndex * xIndex) / (ySize * xSize);

        int R = (int) ((UL.getRed() * ULP) + (UR.getRed() * URP) + (BL.getRed() * BLP) + (BR.getRed() * BRP));
        int G = (int) ((UL.getGreen() * ULP) + (UR.getGreen() * URP) + (BL.getGreen() * BLP) + (BR.getGreen() * BRP));
        int B = (int) ((UL.getBlue() * ULP) + (UR.getBlue() * URP) + (BL.getBlue() * BLP) + (BR.getBlue() * BRP));

        return new Color(R, G, B);
    }

}
