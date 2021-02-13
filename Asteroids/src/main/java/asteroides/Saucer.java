/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroides;

import DLibX.DConsole;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Parker TenBroeck
 */
public class Saucer extends Entity {

    private double xPos = 450, yPos = 200, angle = 0, hitZone;

    private Color color = Color.WHITE;

    private double[] xGraphics = {19, 34, 37, 16, 1, 16, 37, 52, 37, 52, 1, 16};
    private double[] yGraphics = {0, 0, 9, 9, 16, 27, 27, 16, 9, 16, 16, 9};

    public Saucer(int size, DConsole dc) {
        double[][] points = zeroGraphics(xGraphics, yGraphics);
        points = normalizeGraphicsToOne(points[0], points[1]);
        points = multiplyGraphic(points, 20);
        xGraphics = points[0];
        yGraphics = points[1];
        hitZone = averageMagnitude(xGraphics, yGraphics) * 2.5;
    }

    public void draw(DConsole dc) {
        drawPoly(xGraphics, yGraphics, angleRad(), xPos, yPos, color, dc);
    }

    public void update() {

    }

    public double getXPos() {
        return this.xPos;
    }

    public double getYPos() {
        return this.yPos;
    }

    public ArrayList<double[][]> getPoints(DConsole dc) {
        return calcCords(this.xGraphics, this.yGraphics, angleRad(),
                xPos, yPos, dc);
    }

    public double angleRad() {
        return degToRad(angle);
    }

    public ArrayList<double[]> getHitZones(DConsole dc) {
        return makeHitZones(hitZone, this.xPos, this.yPos, dc);
    }

}
