/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActuallyPong.Menu;

import DLibX.DConsole;
import ActuallyPong.RGBcycle;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author tenbroep
 */
public class Menu_Text {

    String text;
    int xPos;
    int yPos;
    RGBcycle RGB;
    Color color;
    int textType;
    int textSize;
    int ORIGIN;

    public Menu_Text(String text, int xPos, int yPos, int textSize, Color color, int ORIGIN) {

        this.text = text;
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
        this.textSize = textSize;
        this.ORIGIN = ORIGIN;
        textType = 0;
    }

    public Menu_Text(String text, int xPos, int yPos, int textSize, RGBcycle RGB, int ORIGIN) {

        this.text = text;
        this.xPos = xPos;
        this.yPos = yPos;
        this.RGB = RGB;
        this.textSize = textSize;
        this.ORIGIN = ORIGIN;
        textType = 1;
    }

    public void draw(DConsole dc) {

        dc.setOrigin(ORIGIN);
        
        if (textType == 0) {
            dc.setPaint(color);
            dc.setFont(new Font("bit9x9", textSize, textSize));
            dc.drawString(text, xPos, yPos - 2);
        } else if (textType == 1) {
            dc.setPaint(new Color(RGB.rgbR, RGB.rgbG, RGB.rgbB));
            dc.setFont(new Font("bit9x9", textSize, textSize));
            dc.drawString(text, xPos , yPos - 2);

        }
    }


}
