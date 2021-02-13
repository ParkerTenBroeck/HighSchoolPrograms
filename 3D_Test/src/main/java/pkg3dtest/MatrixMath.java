/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dtest;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 *
 * @author shelt
 */
public class MatrixMath {

    public MatrixMath() {

    }

    public Vector Mult(double[][] a, Vector v) {

        double[][] m = new double[3][1];
        m[0][0] = v.x;
        m[1][0] = v.y;
        m[2][0] = v.z;

      //  double[][] b = new double[][]{{v.x}, {v.y}, {v.z}};
        return new Vector(Mult(a, m));
    }

    public double[][] Mult(double[][] a, double[][] b) {
        int colsA = a[0].length;
        //System.out.println(colsA + "colsA");
        int rowsA = a.length;
        //System.out.println(rowsA + "rowsA");
        int colsB = b[0].length;
        //System.out.println(colsB + "colsB");
        int rowsB = b.length;
        //System.out.println(rowsB + "rowsB");
        //print(b);

        if (colsA != rowsB) {
            throw new IllegalArgumentException("A:Rows: " + colsA + " did not match B:Columns " + rowsB + ".");
        }
        double result[][] = new double[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }

            }
        }

        return result;
    }

    public double[][] Sub(double[][] a, double[][] b) {
        int colsA = a[0].length;
        int rowsA = a.length;
        int colsB = b[0].length;
        int rowsB = b.length;
        if (colsA != colsB || rowsA != rowsB) {
            System.out.println("rows and cols must equal eachoter");
            return null;
        }

        double[][] result = new double[rowsA][colsA];

        for (int i = 0; i < colsA; i++) {
            for (int j = 0; j < rowsA; j++) {
                result[j][i] = a[j][i] - b[j][i];
            }
        }
        return result;
    }

    public void print(double[][] m) {

        int cols = m[0].length;
        int rows = m.length;

        System.out.println(rows + " x " + cols);
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {
                //   System.out.println(i + " i, " + j + " j");
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }

    }

    public void print(int[][] m) {

        int cols = m[0].length;
        int rows = m.length;

        System.out.println(rows + " x " + cols);
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {
                //   System.out.println(i + " i, " + j + " j");
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }

    }

    public Vector rotateXYZ(Vector v, double y, double b, double a) {

        double[][] m = new double[][]{
            {cos(a) * cos(b), cos(a) * sin(b) * sin(y) - sin(a) * cos(y), cos(a) * sin(b) * cos(y) + sin(a) * sin(y)},
            {sin(a) * cos(b), sin(a) * sin(b) * sin(y) + cos(a) * cos(y), sin(a) * sin(b) * cos(y) - cos(a) * sin(y)},
            {-sin(b), cos(b) * sin(y), cos(b) * cos(y)}
        };
        return Mult(m, v);
    }

    Vector rotateX(Vector v, double angle) {
        double[][] m = new double[][]{
            {1, 0, 0},
            {0, cos(angle), -sin(angle)},
            {0, sin(angle), cos(angle)}
        };
        return Mult(m, v);
    }

    Vector rotateY(Vector v, double angle) {
        double[][] m = new double[][]{
            {cos(angle), 0, Math.sin(angle)},
            {0, 1, 0},
            {-sin(angle), 0, Math.cos(angle)}
        };
        return Mult(m, v);
    }

    Vector rotateZ(Vector v, double angle) {

        double [][] m = new double[][]{
            {cos(angle), -sin(angle), 0},
            {sin(angle), cos(angle), 0},
            {0, 0, 1}
        };
        return Mult(m, v);
    }

}
