/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dtest;

import DLibX.DConsole;
import java.awt.Color;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Parker TenBroeck
 */
public class Camera {

    DConsole dc;
    double xPos;
    double yPos;
    double zPos;

    double xRot;
    double yRot;
    double zRot;

    double offsetX;
    double offsetY;

    double zoom = 1; //idrk what this is suppost to be called also doesnt really work 

    double distance = 500; //the distance of the screen from camera 
    Plane screen;
    Vector screenP = new Vector(0, 0, distance);
    Vector camera;

    MatrixMath mat = new MatrixMath();
    ArrayList<Triangle> triangles = new ArrayList();

    public Camera(int xPos, int yPos, int zPos, double xRot, double yRot, double zRot, double offsetX, double offsetY) {

        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;

        this.xRot = xRot;
        this.yRot = yRot;
        this.zRot = zRot;

        this.offsetX = offsetX;
        this.offsetY = offsetY;

        this.dc = new DConsole();
        dc.setOrigin(DConsole.ORIGIN_CENTER);

    }

    public Camera(int xPos, int yPos, int zPos, double xRot, double yRot, double zRot, double offsetX, double offsetY, DConsole dc) {

        dc.setOrigin(DConsole.ORIGIN_CENTER);

        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;

        this.xRot = xRot;
        this.yRot = yRot;
        this.zRot = zRot;

        this.offsetX = offsetX;
        this.offsetY = offsetY;

        this.dc = dc;

    }

    public void drawPOV(ArrayList<object> obs) {

        camera = new Vector(this.xPos, this.yPos, this.zPos);//makes a point at the camera position

        Vector a = new Vector(this.xPos, this.yPos, this.zPos + distance); // position directly infront of the camera at all times 
        Vector ar = a.subtract(camera);
        ar = mat.rotateX(ar, xRot);
        ar = mat.rotateY(ar, yRot);
        ar = ar.add(camera);

        Vector b = new Vector(1 + this.xPos, this.yPos, this.zPos + distance); // position 1+x from the center of the camera
        Vector br = b.subtract(camera);
        br = mat.rotateX(br, xRot);
        br = mat.rotateY(br, yRot);
        br = br.add(camera);

        Vector c = new Vector(this.xPos, 1 + this.yPos, this.zPos + distance);  // position 1+y from the center of the camera
        Vector cr = c.subtract(camera);
        cr = mat.rotateX(cr, xRot);
        cr = mat.rotateY(cr, yRot);
        cr = cr.add(camera);

        Triangle t = new Triangle(ar, br, cr); //triangle that defines the veiwing plane 

        screen = new Plane(t); // make a plane on triangle t (that the direction youre looking at)

        double[] xp;
        double[] yp;

        Triangle tt;
        Vector arn = new Vector(0, 0, 0).subtract(ar);

        triangles.clear();
        
        for(object o:obs){
            for(polygon p:o.polygons())
            triangles.addAll(p.getTriangles());
        }
        
        for (Triangle k : triangles) {
            k.averageDistance(camera);
            //System.out.println(k.averageDistance);
        }

        dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);

        Collections.sort(triangles, new Comparator<Triangle>() {
            @Override
            public int compare(Triangle t1, Triangle t2) {
                return Double.compare(t2.averageDistance, t1.averageDistance);
            }
        }); 

        for (Triangle triangle : triangles) {

            tt = screen.triangleOfIntersection(camera, triangle);

            tt = tt.translate(arn);
            tt = tt.rotateY(-yRot);
            tt = tt.rotateX(-xRot);

            xp = tt.xPoints(offsetX);
            yp = tt.yPoints(offsetY);

            if (screen.at > 0 && screen.bt > 0 && screen.ct > 0 && triangle.averageDistance <= 700) {
                int wV = (int) map(triangle.averageDistance, triangles.get(0).averageDistance, triangles.get(triangles.size() - 1).averageDistance, 20, 220);
               
                double factor = 0.60;
                int red = (int)(triangle.material.getColor().getRed() * factor + wV * (1.0-factor));
                int green =(int)(triangle.material.getColor().getGreen() * factor + wV * (1.0-factor));
                int blue = (int)(triangle.material.getColor().getBlue() * factor + wV * (1.0-factor));
                //dc.setPaint(triangle.material.getColor());
               
                dc.setPaint(new Color(red, green, blue));
                dc.fillPolygon(xp, yp);
                //dc.setPaint(Color.BLACK);
                //dc.drawPolygon(xp, yp);
            }

        }

        dc.redraw();
        dc.clear();
        //objs.remove(objs.size() - 1); //just removes the triangle that you make uptop there just a reminder
    }

    public void drawPoly(Vector[] nodes, int[][] edges, boolean[] visible) {
        for (int i = 0; i < nodes.length; i++) {
            dc.setPaint(Color.BLACK);
            if (visible[i]) {
                //dc.fillEllipse(nodes[i].x + offsetX, nodes[i].y + offsetY, 5, 5);
            }

        }
        for (int i = 0; i < edges.length; i++) {

            if (visible[edges[i][0]] && visible[edges[i][1]]) {
                dc.drawLine(nodes[edges[i][0]].x + offsetX, nodes[edges[i][0]].y + offsetY,
                        nodes[edges[i][1]].x + offsetX, nodes[edges[i][1]].y + offsetY);
            }

        }
    }

    public double roundToDecimal(double r, int d) {
        int temp = (int) Math.pow(10, d);
        return (double) Math.round(r * temp) / temp;
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
