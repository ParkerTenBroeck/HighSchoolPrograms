/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroides;

import DLibX.DConsole;
import static asteroides.Entity.randomRange;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Parker TenBroeck
 */
public class Asteroid extends Entity {

    private double[] xGraphics = new double[12];
    private double[] yGraphics = new double[12];

    private double xPos, yPos, angle, size, hitZone;

    private Color color = Color.WHITE;

    private Image image;
    private int imageIndex;

    public Asteroid(double x, double y, int size, Texture t) {
        this.xPos = x;
        this.yPos = y;
        this.angle = randomRange(0, 360);
        this.size = size;

        makeAsteroid(size);
        if (this.size <= 3) {
            image = t.getRandomAsteroidTexture(size - 1);
            imageIndex = t.getIndex();
        }
    }

    public Asteroid(Asteroid a, Texture t) {
        this.xPos = a.getXPos();
        this.yPos = a.getYPos();
        this.angle = randomRange(0, 360);
        this.size = a.getSize() - 1;

        makeAsteroid(size);

        if (image == null) {
            image = t.getRandomAsteroidTexture((int)size - 1);
            imageIndex = t.getIndex();
        }

        if (size <= 3) {
            image = t.getImage(a.getImageIndex(), (int) size - 1);
            imageIndex = t.getIndex();
        }

    }

    public void update(DConsole dc) {
        double[] change = moveForward(4 - size, angleRad());
        xPos += change[0];
        yPos += change[1];
        xPos = xCordCheck(xPos, dc);//checks if x is not in the screen anymore
        yPos = yCordCheck(yPos, dc);//checks if y is not in the screen anymore
    }

    public void draw(DConsole dc, Boolean graphic) {

        if (graphic && size <= 3 && image != null) {
            drawImage(image, xPos, yPos, dc);
        } else {
            drawPoly(xGraphics, yGraphics, angleRad(), xPos, yPos, color, dc);
        }
    }

    private int makeAsteroid(double size) {

        double[] point;
        double distance, max = 0, average = 0;

        for (int i = 0; i < xGraphics.length; i++) {

            distance = (size + (Math.random() * size)) * 10;
            point = rotatePoint(distance, 0, degToRad((360 / (xGraphics.length)) * i));

            xGraphics[i] = point[0];
            yGraphics[i] = point[1];

            if (distance > max) {
                max = distance;
            }
            average += distance;
        }
        hitZone = (average / xGraphics.length) * 2;
        return (int) max * 2;
    }

    public double angleRad() {
        return degToRad(angle);
    }

    public double getXPos() {
        return this.xPos;
    }

    public double getYPos() {
        return this.yPos;
    }

    public ArrayList<double[]> getHitZones(DConsole dc) {
        return makeHitZones(this.hitZone, this.xPos, this.yPos, dc);
    }

    public int getSize() {
        return (int) size;
    }

    public int getImageIndex() {
        return imageIndex;
    }
}
