/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dtest;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Parker TenBroeck
 */
public class Material { // the material for this polygon

    public double Ns, Ni, d, Tr, Tf;
    public int illum;
    public double[] Ka, Kd, Ks, Ke;
    public ArrayList<File> textures;

    public Material() {
        Ka = new double[]{1, 1, 1};
    }

    public Color getColor() {
        int r = (int) map(this.Ka[0], 0, 1, 0, 255);
        int g = (int) map(this.Ka[1], 0, 1, 0, 255);
        int b = (int) map(this.Ka[2], 0, 1, 0, 255);

        return new Color(r, g, b);
    }

    final static double EPSILON = 1e-12;

    public double map(double valueCoord1,
            double startCoord1, double endCoord1,
            double startCoord2, double endCoord2) {

        if (Math.abs(endCoord1 - startCoord1) < EPSILON) {
            throw new ArithmeticException("/ 0");
        }

        double offset = startCoord2;
        double ratio = (endCoord2 - startCoord2) / (endCoord1 - startCoord1);
        return ratio * (valueCoord1 - startCoord1) + offset;
    }
}
