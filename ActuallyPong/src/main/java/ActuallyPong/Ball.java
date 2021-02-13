/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActuallyPong;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.Font;

public class Ball {

    DConsole dc;
    Collision collision;
    public int size;
    public double xPos;
    public double yPos;
    public double xV;
    public double yV;
    public double slapSpeed;
    public double totalSpeed;
    private double freezeEffect;
    private boolean isSlapped;
    private int temp;

    public Ball(DConsole dc, Collision collision, int size, int initXPos, int initYPos, double initXVelocity, double initYVelocity) {

        this.dc = dc;
        this.collision = collision;
        this.dc.setOrigin(DConsole.ORIGIN_CENTER);  //sets origin
        this.size = size;
        this.xPos = initXPos;
        this.yPos = initYPos;
        this.xV = initXVelocity;
        this.yV = initYVelocity;

        if (this.xV < 0.7 && this.xV >= 0) {
            this.xV = 0.7;
        } else if (this.xV > -0.7 && this.xV <= 0) {
            this.xV = -0.7;
        }

    }

    public void move(double speed) {
        this.totalSpeed = (speed + Math.abs(this.slapSpeed)) * ((255 - freezeEffect) / 255);
        this.xPos = this.xPos + (xV * this.totalSpeed);
        this.yPos = this.yPos + (yV * this.totalSpeed);
        if (freezeEffect > 0) {
            freezeEffect = freezeEffect - .2;
        }
    }

    public boolean checkCollisionRect(double rectX, double rectY, int rectXSize, int rectYSize, double slapSpeed) {

        return collision.ballToPaddle(this, rectX, rectY, rectXSize, rectYSize, slapSpeed);  //checks if ball hits Paddle

    }

    public void checkIfFreeze(Paddle paddle, int freezeZoneSize) {

        if (paddle.freeze() > 240) {
            if (collision.isCircleInCircle(this.xPos, this.yPos, this.size, paddle.xPos(), paddle.yPos(), freezeZoneSize) == true) {
                freezeEffect = (int) paddle.freeze();
            }
        }
    }

    public boolean checkCollisionWall(int maxX, int minX, int maxY, int minY) {

        return collision.ballToWall(this, maxX, minX, maxY, minY); //checks if ball hiys wall and responds 

    }

    public void collision() {  //slows the ball down from slaps over time 
        if (this.slapSpeed >= 0) {
            this.slapSpeed = this.slapSpeed - .3;
        }
    }

    public boolean isScoreOnLeft(int xBound) {
        return this.xPos <= xBound;
    }

    public boolean isScoreOnRight(int xBound) {
        return this.xPos >= xBound;
    }

    public void draw(boolean debug, int ball, RGBcycle RGB) {             //draws ball and debug info if on
        dc.drawEllipse(this.xPos, this.yPos, this.size, this.size);
        dc.setPaint(new Color(0, 0, (int) freezeEffect));
        dc.fillEllipse(this.xPos, this.yPos, this.size - 2, this.size - 2);
        dc.setPaint(new Color(RGB.rgbR, RGB.rgbG, RGB.rgbB));
        
        if (debug == true) {

            dc.setPaint(Color.WHITE);
            dc.setFont(new Font("bit9x9", 10, 10));
            dc.drawString(ball, this.xPos, this.yPos);
            dc.setPaint(new Color(RGB.rgbR, RGB.rgbG, RGB.rgbB));
        }
    }

    public double xSpeed() {
        return (this.xV * this.totalSpeed);
    }

    public double ySpeed() {
        return (this.yV * this.totalSpeed);
    }

}
