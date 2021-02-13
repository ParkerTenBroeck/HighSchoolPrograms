/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rainingbeans;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 *
 * @author Parker TenBroeck
 */
public class Bean {

    //BufferedImage beanImage;
    Image beanImage;
    double angleDeg, xPos, yPos, size, xV, yV, aV;

    int halfWidth, halfHeight, maxX, maxY;

    public Bean(Dimension dim, BufferedImage buff) {
        BufferedImage beanImage = buff;
        maxX = (int) dim.getWidth();
        maxY = (int) dim.getHeight();
        xPos = randomRange(0, maxX);
        yPos = randomRange(0, maxY);
        size = randomRange(60, 300);
        angleDeg = randomRange(0, 360);
        yV = randomRange(0, 5);
        aV = randomRange(-2, 2);
        this.beanImage = (beanImage.getScaledInstance((int) size, (int) size, Image.SCALE_SMOOTH));
    }

    public void update() {
        xPos += xV;
        yPos += yV;
        checkCords();
        angleDeg += aV;
    }

    public void checkCords() {
        if (yPos - halfHeight > maxY) {
            yPos -= maxY + halfHeight * 2;
        }
        if (yPos + halfHeight < 0) {
            yPos += maxY + halfHeight * 2;
        }
        if (xPos - halfWidth > maxX) {
            xPos -= maxX + halfWidth * 2;
        }
        if (xPos + halfWidth < 0) {
            xPos += maxX + halfWidth * 2;
        }
    }

    public BufferedImage getImage() {

        double rads = Math.toRadians(angleDeg);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = beanImage.getWidth(null);
        int h = beanImage.getHeight(null);
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(beanImage, 0, 0, null);
        g2d.dispose();

        halfWidth = (rotated.getWidth() / 2);
        halfHeight = (rotated.getHeight() / 2);

        return rotated;
    }

    public int getMX() {
        return (int) xPos - halfWidth;
    }

    public int getMY() {
        return (int) yPos - halfHeight;
    }

    public static double randomRange(double min, double max) {
        return (double) ((Math.random() * ((max - min) + 1)) + min);
    }
}
