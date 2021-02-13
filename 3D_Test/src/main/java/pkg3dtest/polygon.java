/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dtest;

import DLibX.DConsole;
import java.awt.Color;
import java.io.File;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shelt
 */
import pkg3dtest.MatrixMath;

public class polygon {

    ArrayList<Triangle> triangles = new ArrayList();
    String[] name;

    Vector[] projected;
    Vector[] nodes;
    int[][] edges = new int[2][];
    double distance = 2;

    double xPos;
    double yPos;
    double zPos;

    double xRot;
    double yRot;
    double zRot;

    public polygon(ArrayList<Triangle> t, double xPos, double yPos, double zPos,
            double xRot, double yRot, double zRot) {

        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;

        this.xRot = xRot;
        this.yRot = yRot;
        this.zRot = zRot;

        this.triangles = t;

        this.projected = new Vector[t.size() * 3];
        nodes = new Vector[t.size() * 3];

        this.edges = new int[t.size() * 3][2];
        System.out.println(t.size());
        for (int i = 0; i < t.size() * 3; i += 3) {
            nodes[i] = t.get(i / 3).a;
            nodes[i + 1] = t.get(i / 3).b;
            nodes[i + 2] = t.get(i / 3).c;

            edges[i][0] = i;
            edges[i][1] = i + 1;
            edges[i + 1][0] = i + 1;
            edges[i + 1][1] = i + 2;
            edges[i + 2][0] = i;
            edges[i + 2][1] = i + 2;

        }
    }

    public void setRotation(double xRot, double yRot, double zRot) {
        this.xRot = xRot;
        this.yRot = yRot;
        this.zRot = zRot;
        //update();
    }

    public void setTranslation(double xPos, double yPos, double zPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        //update();
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public polygon() {

    }

    public double[][] vectToArray(Vector[] v) {
        double[][] a = new double[3][v.length];

        for (int i = 0; i < v.length; i++) {
            a[0][i] = v[i].x;
            a[1][i] = v[i].y;
            a[2][i] = v[i].z;
        }
        return a;

    }

    public Vector[] getNodes() {
        MatrixMath m = new MatrixMath();
        for (int i = 0; i < nodes.length; i++) {

            Vector rotated = m.rotateXYZ(nodes[i], xRot, yRot, zRot); //rotates
            rotated.add(xPos, yPos, zPos); //add translation 

            projected[i] = rotated;
        }
        return projected;
    }

    public ArrayList getTriangles() {
        ArrayList<Triangle> tr = new ArrayList();
        Triangle tempt;
        for (Triangle t : triangles) {
            tempt = t;
            tempt = tempt.rotateXYZ(xRot, yRot, zRot);
            tempt = tempt.translate(xPos, yPos, zPos);

            tr.add(tempt);
        }
        return tr;
    }

    public void addTriangle(Triangle t) {
        triangles.add(t);
    }

}
