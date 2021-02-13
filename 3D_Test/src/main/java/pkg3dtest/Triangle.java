/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dtest;

import java.awt.Color;
import java.util.Arrays;

/**
 *
 * @author tenbroep
 */
public class Triangle implements Comparable {

    Vector a;
    Vector b;
    Vector c;
    double t;
    double averageDistance;
    MatrixMath m = new MatrixMath();
    Material material = new Material();

    public Triangle(Vector a, Vector b, Vector c) {

        this.a = a;
        this.b = b;
        this.c = c;

    }

    public Triangle(Vector a, Vector b, Vector c, Material m) {

        this.a = a;
        this.b = b;
        this.c = c;
        this.material = m;

    }

    public boolean isPointInTriangle(Vector p) {
        double d = determinant(this.a, this.b, this.c);
        double d1 = determinant(p, this.b, this.c);
        double d2 = determinant(this.a, p, this.c);
        double d3 = determinant(this.a, this.b, p);

        double aw = d1 / d; //findes the weights for a, b and c 
        double bw = d2 / d;
        double cw = d3 / d;

        return aw > 0 && bw > 0 && cw > 0;
    }

    public Color getColor() {
        return material.getColor();
    }

    public boolean isLineIntersecting(Vector p1, Vector p2) {
        Plane pt = new Plane(this);
        this.t = pt.t;
        Vector p = pt.pointOfIntersection(p1, p2);
        return isPointInTriangle(p);

    }

    public double determinant(Vector a, Vector b, Vector c) {

        double tempA = a.x * (b.y * c.z - b.z * c.y);
        double tempB = a.y * (b.x * c.z - b.z * c.x);
        double tempC = a.z * (b.x * c.y - b.y * c.x);
        return tempA - tempB + tempC;
    }

    public Triangle translate(double xt, double yt, double zt) {
        Vector t = new Vector(xt, yt, zt);
        Vector at = a.add(t);
        Vector bt = b.add(t);
        Vector ct = c.add(t);

        return new Triangle(at, bt, ct, material);
    }

    public Triangle translate(Vector v) {
        Vector at = a.add(v);
        Vector bt = b.add(v);
        Vector ct = c.add(v);

        return new Triangle(at, bt, ct, material);
    }

    public Triangle rotateX(double xr) {
        Vector ar = m.rotateX(a, xr);
        Vector br = m.rotateX(b, xr);
        Vector cr = m.rotateX(c, xr);

        return new Triangle(ar, br, cr, material);
    }

    public Triangle rotateY(double yr) {
        Vector ar = m.rotateY(a, yr);
        Vector br = m.rotateY(b, yr);
        Vector cr = m.rotateY(c, yr);

        return new Triangle(ar, br, cr, material);
    }

    public Triangle rotateZ(double zr) {
        Vector ar = m.rotateZ(a, zr);
        Vector br = m.rotateZ(b, zr);
        Vector cr = m.rotateZ(c, zr);

        return new Triangle(ar, br, cr, material);
    }

    public Triangle rotateXYZ(double xr, double yr, double zr) {
        Vector ar = m.rotateXYZ(a, xr, yr, zr);
        Vector br = m.rotateXYZ(b, xr, yr, zr);
        Vector cr = m.rotateXYZ(c, xr, yr, zr);

        return new Triangle(ar, br, cr, material);
    }

    public void averageDistance(Vector t) {
        double aT = this.a.distance(t);
        double bT = this.b.distance(t);
        double cT = this.c.distance(t);
        averageDistance = (aT + bT + cT) / 3;
    }

    public double[] xPoints(double tx) {
        return new double[]{a.x + tx, b.x + tx, c.x + tx};
    }

    public double[] yPoints(double ty) {
        return new double[]{a.y + ty, b.y + ty, c.y + ty};
    }

    public double getDistance() {
        return t;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Triangle) {
            Triangle t = (Triangle) o;

            return (int) (this.averageDistance - t.averageDistance);

        } else {
            return -1;
        }
    }

    public void setRotationXYZ(double xRot, double yRot, double zRot, Triangle t) {
        Triangle temp = t.rotateXYZ(xRot, yRot, zRot);
        a = temp.a;
        b = temp.b;
        c = temp.c;
    }

    public void setTranslation(double xPos, double yPos, double zPos, Triangle t) {
        Triangle temp = t.translate(xPos, yPos, zPos);
        a = temp.a;
        b = temp.b;
        c = temp.c;
    }

}
