/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dtest;

import java.util.ArrayList;

public class object {

    double xPos;
    double yPos;
    double zPos;

    double xRot;
    double yRot;
    double zRot;

    ArrayList<polygon> polygons = new ArrayList();

    public object(ArrayList<polygon> p) {
        this.polygons = p;
        update();
    }

    public object() {

    }

    public object(double xPos, double yPos, double zPos,
            double xRot, double yRot, double zRot) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;

        this.xRot = xRot;
        this.yRot = yRot;
        this.zRot = zRot;
        update();

    }

    public void addPolygon(polygon p) {
        p.setRotation(xRot, yRot, zRot);
        p.setTranslation(xPos, yPos, zPos);
        polygons.add(p);
        update();
    }

    public ArrayList<polygon> polygons() {
        return polygons;
    }

    public void xRotate(double xRot) {
        this.xRot += xRot;
        update();
    }

    public void yRotate(double yRot) {
        this.yRot += yRot;
        update();
    }

    public void zRotate(double zRot) {
        this.zRot += zRot;
        update();
    }

    public void update() {
        for (polygon p : polygons) {
            p.setRotation(xRot, yRot, zRot);
            p.setTranslation(xPos, yPos, zPos);
        }
    }
}
