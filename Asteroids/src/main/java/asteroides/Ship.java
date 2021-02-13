/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroides;

import DLibX.DConsole;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author tenbroep
 */
public class Ship extends Entity {

    private double xPos = 0, yPos = 0, angle = 180, hitZone;

    private double xMomentum = 0, yMomentum = 0, drag = 0.04, limit = 9;

    private Color color = Color.WHITE;

    private boolean show = true;

    private final double[] xGraphics = {0, -18, 0, 18};
    private final double[] yGraphics = {24, -24, -18, -24};

    public Ship(double xPos, double yPos) {

        this.xPos = xPos;
        this.yPos = yPos;
        hitZone = averageMagnitude(xGraphics, yGraphics) * 1.5;

    }

    public void draw(DConsole dc) {

        if (show) {
            drawPoly(xGraphics, yGraphics, angleRad(), xPos, yPos, color, dc);
            //dc.drawLine(xPos, yPos, xPos + xMomentum * 3, yPos + yMomentum * 3);
        }
    }

    public void draw(DConsole dc, Color color) {

        if (show) {
            drawPoly(xGraphics, yGraphics, angleRad(), xPos, yPos, color, dc);
            //dc.drawLine(xPos, yPos, xPos + xMomentum * 3, yPos + yMomentum * 3);
        }
    }

    public void moveForwards(double speed) {
        double[] change = moveForward(speed, angleRad());

        if (Math.abs(xMomentum) <= Math.abs(rotatePoint(0, limit, angleRad())[0])) {
            xMomentum += change[0];
        }
        if (Math.abs(yMomentum) <= Math.abs(rotatePoint(0, limit, angleRad())[1])) {
            yMomentum += change[1];
        }
    }

    public Bullet shoot() {
        double[] point = rotatePoint(0, 30, angleRad());
        point[0] += xPos;
        point[1] += yPos;

        return new Bullet(point[0], point[1], angle, 50,
                10 + magnitude(xMomentum, yMomentum));
    }

    public ArrayList<Bullet> superShoot(int numberOfBullets) {
        ArrayList<Bullet> bullets = new ArrayList();

        for (int i = 0; i < numberOfBullets; i++) {

            double angle = degToRad((360/ numberOfBullets) * i);
            
            //double[] point = new double[2];
            double[] point = rotatePoint(0, 30, angle);
            point[0] += xPos;
            point[1] += yPos;

            bullets.add(new Bullet(point[0], point[1], this.angle, 100,
                    10 + magnitude(xMomentum, yMomentum)));
        }

        return bullets;
    }
        public ArrayList<Bullet> sheild(int numberOfBullets) {
        ArrayList<Bullet> bullets = new ArrayList();

        for (int i = 0; i < numberOfBullets; i++) {

            double angle = degToRad((360/ numberOfBullets) * i);
            
            //double[] point = new double[2];
            double[] point = rotatePoint(0, 30, angle);
            point[0] += xPos;
            point[1] += yPos;

            bullets.add(new Bullet(point[0], point[1], this.angle, 2, 0));
        }

        return bullets;
    }


    public void rotate(double angle) {
        this.angle += angle;
    }

    public void hyperSpace(DConsole dc) {
        xMomentum = 0;
        yMomentum = 0;
        xPos = randomRange(0, dc.getWidth());
        yPos = randomRange(0, dc.getHeight());
    }

    public double angleRad() {
        return degToRad(angle);
    }

    public void updateMomentum(DConsole dc) {

        this.xPos += xMomentum;
        this.yPos += yMomentum;

        xPos = xCordCheck(xPos, dc);//checks if x is not in the screen anymore
        yPos = yCordCheck(yPos, dc);//checks if y is not in the screen anymore

        double[] dragComponents = moveForward(drag, findVectorAngle(xMomentum, yMomentum)); //finds the angle of travel and splits the drag into x and y components

        if (Math.abs(this.xMomentum) > dragComponents[0]) {
            this.xMomentum = noSignSubtraction(xMomentum, dragComponents[0]);
        } else {
            this.xMomentum = 0;
        }

        if (Math.abs(this.yMomentum) > dragComponents[1]) {
            this.yMomentum = noSignSubtraction(yMomentum, dragComponents[1]);
        } else {
            this.yMomentum = 0;
        }
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

    public void setVisibility(boolean s) {
        show = s;
    }

    public void invertVisibility() {
        show = !show;
    }

    public ArrayList<double[]> getHitZones(DConsole dc) {
        return makeHitZones(hitZone, this.xPos, this.yPos, dc);
    }

}
