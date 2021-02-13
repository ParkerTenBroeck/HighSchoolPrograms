/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroides;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.Image;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Parker TenBroeck
 */
public class Entity {

    public void drawPoly(double[] x, double[] y, double angle,
            double xPos, double yPos, Color color, DConsole dc) {

        dc.setPaint(color);

        ArrayList<double[][]> cords = calcCords(x, y, angle, xPos, yPos, dc);

        for (double[][] c : cords) {
            dc.drawPolygon(c[0], c[1]);
        }
    }

    public double[][] getAllHitPoints(double[] xPoints, double[] yPoints,
            double angle, double xPos, double yPos, DConsole dc) {

        ArrayList<double[][]> cords = calcCords(xPoints, yPoints, angle, xPos, yPos, dc);

        for (double[][] c : cords) {
            //dc.drawPolygon(c[0], c[1]);
        }
        return null;

    }

    public ArrayList<double[][]> calcCords(double[] x, double[] y, double angle,
            double xPos, double yPos, DConsole dc) {

        ArrayList<double[][]> calcCords = new ArrayList();
        double[][] cords = rotatePoly(x, y, angle, xPos, yPos);
        calcCords.add(cords);

        double yPosNew = yPos;
        double xPosNew = xPos;

        for (double xi : cords[0]) {

            if (xi < 0 || xi > dc.getWidth()) {

                if (xi < 0) {
                    xPosNew += dc.getWidth();
                }
                if (xi > dc.getWidth()) {
                    xPosNew -= dc.getWidth();
                }
                cords = rotatePoly(x, y, angle, xPosNew, yPos);
                calcCords.add(cords);
                break;
            }
        }
        for (double yi : cords[1]) {

            if (yi < 0 || yi > dc.getHeight()) {

                if (yi < 0) {
                    yPosNew += dc.getHeight();
                }
                if (yi > dc.getHeight()) {
                    yPosNew -= dc.getHeight();
                }
                cords = rotatePoly(x, y, angle, xPos, yPosNew);
                calcCords.add(cords);
                break;
            }
        }
        if (xPosNew != xPos && yPosNew != yPos) {
            cords = rotatePoly(x, y, angle, xPosNew, yPosNew);
            calcCords.add(cords);
        }
        return calcCords;
    }

    public void drawImage(Image image, double xPos, double yPos, DConsole dc) {

        dc.drawImage(image, xPos, yPos);

        double yPosNew = yPos;
        double xPosNew = xPos;
        double imageW = image.getWidth(null) / 2;
        double imageH = image.getHeight(null) / 2;

        if (xPos - imageW / 2 < 0 || xPos + imageW > dc.getWidth()) {

            if (xPos - imageW < 0) {
                xPosNew += dc.getWidth();
            }
            if (xPos + imageW > dc.getWidth()) {
                xPosNew -= dc.getWidth();
            }
            dc.drawImage(image, xPosNew, yPos);
        }

        if (yPos - imageH < 0 || yPos + imageH > dc.getHeight()) {

            if (yPos - imageH < 0) {
                yPosNew += dc.getHeight();
            }
            if (yPos + imageH > dc.getHeight()) {
                yPosNew -= dc.getHeight();
            }
            dc.drawImage(image, xPos, yPosNew);
        }

        if (xPosNew != xPos && yPosNew != yPos) {
            dc.drawImage(image, xPosNew, yPosNew);
        }

    }

    public double[][] rotatePoly(double[] x, double[] y, double angle, double xPos, double yPos) { // rotates a pollys graphic
        double[][] cords = new double[2][x.length];

        for (int i = 0; i < cords[0].length; i++) {
            double[] point = rotatePoint(x[i], y[i], angle);
            cords[0][i] = point[0];
            cords[1][i] = point[1];
            cords[0][i] += xPos;
            cords[1][i] += yPos;
        }
        return cords;

    }

    public double xCordCheck(double x, DConsole dc) {

        if (x < 0) {
            x += dc.getWidth();
        }
        if (x > dc.getWidth()) {
            x -= dc.getWidth();
        }
        return x;
    }

    public double yCordCheck(double y, DConsole dc) {
        if (y < 0) {
            y += dc.getHeight();
        }
        if (y > dc.getHeight()) {
            y -= dc.getHeight();
        }
        return y;
    }

    public double findVectorAngle(double xChange, double yChange) {
        return Math.atan2(xChange, yChange);
    }

    public double noSignSubtraction(double minuend, double subtrahend) { //removes the subtrahend no matter the sign and returns with the original sign
        return (Math.abs(minuend) - Math.abs(subtrahend)) * Math.signum(minuend);
    }

    public double radToDeg(double angle) { //radiance to degrees
        return angle * 180 / Math.PI;
    }

    public double degToRad(double angle) { //degrees to radiance
        return angle * Math.PI / 180;
    }

    public double[] rotatePoint(double x, double y, double angle) { // rotates a point over an angle
        double s = sin(angle);
        double c = cos(angle);

        double xnew = x * c - y * s;
        double ynew = x * s + y * c;

        return new double[]{xnew, ynew};
    }

    public double[] moveForward(double movement, double angle) { //moves the entity fowards a distance depending on where its facing 
        return moveRelative(0, movement, angle);
    }

    public double[] moveRelative(double xChange, double yChange, double angle) {
        return rotatePoint(xChange, yChange, angle);
    }

    public static double randomRange(double min, double max) {
        double x = (double) ((Math.random() * ((max - min) + 1)) + min);
        return x;
    }

    public double magnitude(double xChange, double yChange) {
        return Math.sqrt((xChange * xChange) + (yChange * yChange));
    }

    public double[] magnitudeAndAngleToPoint(double magnitude, double angle) {
        return rotatePoint(0, magnitude, angle);
    }

    public Image resizeImage(int x, int y, Image image) {
        return image.getScaledInstance(x, y, Image.SCALE_DEFAULT);
    }

    public double[][] zeroGraphics(double[] xPoints, double[] yPoints) {

        double xAverage = 0;
        double yAverage = 0;

        for (double x : xPoints) {
            xAverage += x;
        }
        xAverage /= xPoints.length;

        for (double y : yPoints) {
            yAverage += y;
        }
        yAverage /= yPoints.length;

        for (int i = 0; i < xPoints.length; i++) {
            xPoints[i] -= xAverage;
        }

        for (int i = 0; i < yPoints.length; i++) {
            yPoints[i] -= yAverage;
        }
        return new double[][]{xPoints, yPoints};
    }

    public double[][] normalizeGraphicsToOne(double[] xPoints, double[] yPoints) {

        int length = xPoints.length;

        double[] angle = new double[length];
        double[] magnitude = new double[length];
        double maxM = 0;

        double[][] points = new double[2][length];

        for (int i = 0; i < length; i++) {

            magnitude[i] = magnitude(xPoints[i], yPoints[i]);
            angle[i] = findVectorAngle(xPoints[i], yPoints[i]);

            if (maxM < magnitude[i]) {
                maxM = magnitude[i];
            }
        }

        for (int i = 0; i < length; i++) {
            magnitude[i] /= maxM;
        }

        for (int i = 0; i < length; i++) {

            double point[] = magnitudeAndAngleToPoint(magnitude[i], angle[i]);
            points[0][i] = point[0];
            points[1][i] = point[1];
        }
        return points;
    }

    public double[][] multiplyGraphic(double[][] points, double multiplicationFactor) {

        for (int i = 0; i < points[0].length; i++) {
            points[0][i] *= multiplicationFactor;
            points[1][i] *= multiplicationFactor;
        }
        return points;
    }

    public ArrayList<double[]> makeHitZones(double radius, double xPos, double yPos, DConsole dc) {

        ArrayList<double[]> circles = new ArrayList();
        circles.add(new double[]{radius, xPos, yPos});

        double yPosNew = yPos;
        double xPosNew = xPos;

        if (xPos - radius < 0 || xPos + radius > dc.getWidth()) {

            if (xPos - radius < 0) {
                xPosNew += dc.getWidth();
            }
            if (xPos + radius > dc.getWidth()) {
                xPosNew -= dc.getWidth();
            }
            circles.add(new double[]{radius, xPosNew, yPos});
        }

        if (yPos - radius < 0 || yPos + radius > dc.getHeight()) {

            if (yPos - radius < 0) {
                yPosNew += dc.getHeight();
            }
            if (yPos + radius > dc.getHeight()) {
                yPosNew -= dc.getHeight();
            }
            circles.add(new double[]{radius, xPos, yPosNew});
        }
        if (xPosNew != xPos && yPosNew != yPos) {
            circles.add(new double[]{radius, xPosNew, yPosNew});
        }
        return circles;
    }

    public double averageMagnitude(double[] x, double y[]) {

        double average = 0;
        for (int i = 0; i < x.length; i++) {
            average += magnitude(x[i], y[i]);
        }
        average /= x.length;
        return average;
    }

}
