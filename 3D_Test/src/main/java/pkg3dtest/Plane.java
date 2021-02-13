/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dtest;

/**
 *
 * @author tenbroep
 */
public class Plane {

    double a;
    double b;
    double c;
    double d;
    double t;
    double at;
    double bt;
    double ct;

    public Plane(Triangle t) {

        Vector ab = t.b.subtract(t.a);
        Vector ac = t.c.subtract(t.a);
        MatrixMath mat = new MatrixMath();

        Vector v = ab.crossProduct(ac);

        this.a = v.x;
        this.b = v.y;
        this.c = v.z;

        d = -1 * ((this.a * t.a.x) + (this.b * t.a.y) + (this.c * t.a.z));
    }

    public Plane(Vector a, Vector b, Vector c) {

        Vector ab = b.subtract(a);
        Vector ac = c.subtract(a);
        MatrixMath mat = new MatrixMath();
        mat.print(ab.array());
        mat.print(ac.array());

        Vector v = ab.crossProduct(ac);

        this.a = v.x;
        this.b = v.y;
        this.c = v.z;

        d = -1 * ((this.a * a.x) + (this.b * a.y) + (this.c * a.z));

    }

    public Triangle triangleOfIntersection(Vector point1, Triangle tri) {

        Vector ai = pointOfIntersection(point1, tri.a);
        at = t;
        Vector bi = pointOfIntersection(point1, tri.b);
        bt = t;
        Vector ci = pointOfIntersection(point1, tri.c);
        ct = t;

        return new Triangle(ai, bi, ci);
    }

    public Vector pointOfIntersection(Vector point1, Vector point2) {
        double t;

        double aP1x = a * point1.x;
        double bP1y = b * point1.y;
        double cP1z = c * point1.z;

        double aP2x = a * point2.x;
        double bP2y = b * point2.y;
        double cP2z = c * point2.z;

        double top = (-1 * (aP1x + bP1y + cP1z + d));
        double bottom = ((-1 * aP1x) + aP2x + (-1 * bP1y) + bP2y + (-1 * cP1z) + cP2z);

        t = top / bottom;
        this.t = t;

        return getPointFromT(t, point1, point2);
    }

    public Vector getPointFromT(double t, Vector point1, Vector point2) {
        double rX = Rt(t, point1.x, point2.x);
        double rY = Rt(t, point1.y, point2.y);
        double rZ = Rt(t, point1.z, point2.z);

        return new Vector(rX, rY, rZ);
    }

    public double Rt(double t, double point1, double point2) {
        return ((1 - t) * point1) + (point2 * t);
    }
}
