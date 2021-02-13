/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroides;

import DLibX.DConsole;

/**
 *
 * @author Parker TenBroeck
 */
public class Particle extends Entity {

    private double xPos, yPos, angle, life, speed;

    public Particle(double xPos, double yPos, double angle, int life, double speed) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.angle = angle;
        this.life = life;
        this.speed = speed;
    }

    public void draw(DConsole dc) {
        dc.drawPoint(xPos, yPos);
    }

    public boolean update(DConsole dc) {

        double[] point = moveForward(speed, degToRad(angle));
        xPos += point[0];
        yPos += point[1];
        xPos = xCordCheck(xPos, dc);//checks if x is not in the screen anymore
        yPos = yCordCheck(yPos, dc);//checks if y is not in the screen anymore
        life--;
        return life <= 0;
    }

}
