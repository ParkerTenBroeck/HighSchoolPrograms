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
public class Vector {

    public double x;
    public double y;
    public double z;
    public Vector nv;
    public Vector tv;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(double[][] a) {
        this.x = a[0][0];
        this.y = a[1][0];
        if (a.length > 2) {
            this.z = a[2][0];
        }
    }

    public double[][] array() {
        double[][] array = new double[3][1];
        array[0][0] = this.x;
        array[1][0] = this.y;
        array[2][0] = this.z;
        return array;
    }

    public void add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public Vector add(Vector v) {
        return new Vector(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public Vector subtract(Vector v) {
        return new Vector(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    public Vector crossProduct(Vector b) {

        double x = (this.y * b.z) - (b.y * this.z);
        double y = (this.z * b.x) - (b.z * this.x);
        double z = (this.x * b.y) - (b.x * this.y);

        return new Vector(x, y, z);
    }

    public double dotProduct(Vector b) {
        return (this.x * b.x) + (this.y * b.y) + (this.z * b.z);
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
    }
    public double distance(Vector v2) {
        return Math.sqrt(Math.pow(this.x - v2.x, 2) + Math.pow(this.y - v2.y, 2) + Math.pow(this.z - v2.z, 2));
    }

    public double angleOfIntersection(Vector p2) {
        return (Math.acos(this.dotProduct(p2) / (this.magnitude() * p2.magnitude())));
    }

    public double tOfIntersection(Vector v1, Vector p2, Vector v2) {
        Vector p1 = this;
        double top = p1.y - p2.y - ((p1.x / v2.x) * v2.y) + ((p2.x / v2.x) * v2.y);
        double bottom = ((v1.x / v2.x) * v2.y) - v1.y;
        if(false == true){
        System.out.println(p1.x + "p1.x");
        System.out.println(p1.y + "p1.y");
        System.out.println(v1.x + "v1.x");
        System.out.println(v1.y + "v1.y");
        System.out.println(p2.x + "p2.x");
        System.out.println(p2.y + "p2.y");
        System.out.println(v2.x + "v2.x");
        System.out.println(v2.y + "v2.y ");
        }
        return top / bottom;
    }
    public void addNormalVector(Vector nv){
        this.nv = nv;
    }
        public void addTextureVector(Vector nv){
        this.tv = tv;
    }

}
