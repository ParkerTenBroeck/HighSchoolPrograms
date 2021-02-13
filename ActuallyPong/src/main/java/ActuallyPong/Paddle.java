/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActuallyPong;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

/**
 *
 * @author tenbroep
 */
public class Paddle {

    DConsole dc;
    Paddle_AI AI;
    Collision collision;
    private int slapChange;
    private double xPos;
    private double yPos;
    private int sizeX;
    private int sizeY;
    private final int upKey;
    private final int downKey;
    private final int leftKey;
    private final int rightKey;
    private double slapPower;
    private double slap;
    private double freezeCoolDown;
    private double dashCoolDown = 0;
    public double dashSpeed = 0;
    private int freezeZoneSize = 400;
    private double speed;
    private boolean isAI;
    private Double_Press dashUp;
    private Double_Press dashDown;

    public Paddle(DConsole dc, List<Ball> ball, Collision collision, int initXPos, int initYPos, int sizeX, int sizeY,
            int upKey, int downKey, int leftKey, int rightKey, boolean isAI, int difficulty, int powerUps, int team) {

        this.dc = dc;
        this.collision = collision;
        this.dashUp = new Double_Press(dc, 20, upKey);
        this.dashDown = new Double_Press(dc, 20, downKey);
        this.dc.setOrigin(DConsole.ORIGIN_CENTER);  //sets origin
        this.xPos = initXPos;
        this.yPos = initYPos;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.isAI = isAI;
        if (isAI == true) {
            Paddle_AI AI = new Paddle_AI(this, ball, collision, dc, difficulty, powerUps, team); //creates the AI
            this.AI = AI;
        }
    }

    public void move(double speed) {

        slap = 0;
        this.xPos = this.xPos - (int) (this.slapPower * 3);

        if (dc.isKeyPressed(this.upKey)) { //moving up
            this.yPos = this.yPos - speed * (this.dashSpeed + 1);
        }
        if (dc.isKeyPressed(this.downKey)) { //moving down
            this.yPos = this.yPos + speed * (this.dashSpeed + 1);
        }
        if (dashCoolDown <= 0) {
            if (this.dashUp.isDoublePressed()) {
                dashCoolDown = 255;
            }
            if (this.dashDown.isDoublePressed()) {
                dashCoolDown = 255;
            }
        }
        if (dashCoolDown >= ((255 * 3) / 4)) {
            this.dashSpeed = (dashCoolDown - 191.25) / 21.25;
        } else {
            this.dashSpeed = 0;
        }

        if (freezeCoolDown > 0) {
            freezeCoolDown = freezeCoolDown - .1;
        }
        if (dashCoolDown > 0) {
            dashCoolDown = dashCoolDown - .1;
        }

        if (dc.isKeyPressed(this.rightKey) && dc.isKeyPressed(this.leftKey)) {

            if ((int) freezeCoolDown == 0) {
                freezeCoolDown = 255;
            }

        } else if (dc.isKeyPressed(this.rightKey)) {  //slapping left
            if (slapPower < 5) {
                slapPower = slapPower + 0.1;
            }
        } else if (dc.isKeyPressed(this.leftKey)) {  //slaping right 
            if (slapPower > -5) {
                slapPower = slapPower - 0.1;
            }
        } else {
            if (slapPower != 0 && (!dc.isKeyPressed(this.leftKey) && !dc.isKeyPressed(this.rightKey))) {
                slap = slapPower;
            }
            slapPower = 0;
        }
        this.xPos = this.xPos + (int) (this.slapPower * 3);
    }

    public void draw(boolean debug, int ball, RGBcycle RGB, int freezeZoneSize) {
        if (freezeCoolDown >= 240) {
            dc.setPaint(new Color(0, 0, ((int) freezeCoolDown) - (255 - (int) freezeCoolDown + 1) * (255 - 240))); //just makes it fade away nicely 
            dc.fillEllipse(xPos, yPos, freezeZoneSize, freezeZoneSize);  //draws freeze zone
            dc.setPaint(new Color(RGBcycle.rgbR, RGBcycle.rgbG, RGBcycle.rgbB));
        }
        int ranX = (int) randomRange(this.slapPower * -1, this.slapPower * 1);  //creates a random jiggle based from slap power
        int ranY = (int) randomRange(this.slapPower * -1, this.slapPower * 1);  //creates a random jiggle based from slap power
        dc.drawRect(this.xPos + ranX, this.yPos + ranY, this.sizeX, this.sizeY); //draws main paddle
        dc.setPaint(new Color((int) ((double) Math.abs(slapPower) * (double) 50), (int) dashCoolDown, (int) freezeCoolDown));  //sets paint to a red based on slap power
        dc.fillRect(this.xPos + ranX, this.yPos + ranY, this.sizeX - 2, this.sizeY - 2);  //creates a slap indicator inside the paddle 
        dc.setPaint(new Color(RGBcycle.rgbR, RGBcycle.rgbG, RGBcycle.rgbB));  //sets paint back to RGB
        if (debug == true) {
            dc.setPaint(Color.WHITE);
            dc.setFont(new Font("bit9x9", 10, 10));
            dc.drawString(ball, this.xPos, this.yPos);
            dc.setPaint(new Color(RGB.rgbR, RGB.rgbG, RGB.rgbB));
        }
    }

    public void moveAI(double xChange, double yChange, boolean leftKey, boolean rightKey, boolean dash, boolean freeze) {

        slap = 0;
        this.xPos = this.xPos - (int) (this.slapPower * 3);

        this.yPos = this.yPos + yChange;

        this.xPos = this.xPos + xChange;

        if (dashCoolDown <= 0) {
            if (dash) {
                dashCoolDown = 255;
            }
        }
        if (dashCoolDown >= ((255 * 3) / 4)) {  //only gives you powers for a quarter of the cooldown time
            this.dashSpeed = (dashCoolDown - 191.25) / 21.25;  //slowly ramps down the speed 
        } else {
            this.dashSpeed = 0;
        }

        if (freeze) {

            if ((int) freezeCoolDown == 0) {
                freezeCoolDown = 255;
            }

        }

        if (freezeCoolDown > 0) {
            freezeCoolDown = freezeCoolDown - .1;  //freeze cool down 
        }
        if (dashCoolDown > 0) {
            dashCoolDown = dashCoolDown - .1;   //dash cool down 
        }
        
        if (rightKey == true) {  //slapping left
            if (slapPower < 5) {
                slapPower = slapPower + 0.1;
            }
        } else if (leftKey == true) {  //slaping right 
            if (slapPower > -5) {
                slapPower = slapPower - 0.1;
            }
        } else {
            if (slapPower != 0 && (leftKey != true && rightKey != true)) {
                slap = slapPower;
            }
            slapPower = 0;
        }
        this.xPos = this.xPos + (int) (this.slapPower * 3);
    }

    public void AI(boolean debug) {
        this.AI.think(debug);
    }

    public double xPos() {
        return this.xPos;
    }

    public double yPos() {
        return this.yPos;
    }

    public int sizeX() {
        return this.sizeX;
    }

    public int sizeY() {
        return this.sizeY;
    }

    public double slap() {
        return this.slap;
    }

    public double freeze() {
        return (int) this.freezeCoolDown;
    }

    public boolean isAI() {
        return this.isAI;
    }

    private static double randomRange(double min, double max) {
        double x = (double) ((Math.random() * ((max - min) + 1)) + min);
        return x;
    }

    public double xPosAI() {
        return this.xPos - (this.slapPower * 3);
    }

}
