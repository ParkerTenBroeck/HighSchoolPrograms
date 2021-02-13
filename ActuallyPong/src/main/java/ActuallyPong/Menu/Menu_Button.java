/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActuallyPong.Menu;

import DLibX.DConsole;
import ActuallyPong.RGBcycle;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.List;

/**
 *
 * @author tenbroep
 */
public class Menu_Button {

    int xPos;
    int yPos;
    int xSize;
    int ySize;
    int thickness;
    Color line;
    Color inside;
    Color hover;
    Color select;
    boolean isClicked = false;
    RGBcycle hoverRGB;
    RGBcycle selectRGB;
    int type;
    boolean isInGroup = false;
    boolean enabled = true;
    String name;
    Menu_Button_Group group;

    public Menu_Button(String name, int xPos, int yPos, int xSize, int ySize, int thickness, Color line, Color inside, Color hover, Color select) {

        this.xPos = xPos;
        this.yPos = yPos;
        this.xSize = xSize;
        this.ySize = ySize;
        this.thickness = thickness;
        this.line = line;
        this.inside = inside;
        this.hover = hover;
        this.select = select;
        this.type = 0;
        this.name = name;

    }

    public Menu_Button(String name, int xPos, int yPos, int xSize, int ySize, int thickness, Color line, Color inside, RGBcycle hover, Color select) {

        this.xPos = xPos;
        this.yPos = yPos;
        this.xSize = xSize;
        this.ySize = ySize;
        this.thickness = thickness;
        this.line = line;
        this.inside = inside;
        this.hoverRGB = hover;
        this.select = select;
        this.type = 1;
        this.name = name;

    }

    public Menu_Button(String name, int xPos, int yPos, int xSize, int ySize, int thickness, Color line, Color inside, Color hover, RGBcycle select) {

        this.xPos = xPos;
        this.yPos = yPos;
        this.xSize = xSize;
        this.ySize = ySize;
        this.thickness = thickness;
        this.line = line;
        this.inside = inside;
        this.hover = hover;
        this.selectRGB = select;
        this.type = 2;
        this.name = name;

    }

    public void draw(DConsole dc) {

        if(!enabled) { 
          return;
        }
        dc.setOrigin(DConsole.ORIGIN_CENTER);
        dc.setStroke(new BasicStroke(thickness));

        dc.setPaint(inside);
        if (isMouseInside(dc)) {
            if (this.type != 1) {
                dc.setPaint(hover);
            } else if (this.type == 1) {
                dc.setPaint(hoverRGB.color());
            }
            if (dc.getMouseButton(1)) {
                if (isInGroup == true) {
                    group.clear();
                    this.isClicked = !this.isClicked;
                } else {

                    this.isClicked = !this.isClicked;
                }
            }
        }
        if (this.isClicked == true) {
            if (this.type != 2) {
                dc.setPaint(select);
            } else {
                dc.setPaint(selectRGB.color());
            }
            dc.fillRect(xPos, yPos, xSize, ySize);
        }
        dc.fillRect(xPos, yPos, xSize, ySize);
        dc.setPaint(line);
        dc.drawRect(xPos, yPos, xSize, ySize);
    }

    private boolean isMouseInside(DConsole dc) {

        return ((dc.getMouseXPosition() <= (this.xPos + (this.xSize / 2))) && (dc.getMouseXPosition() >= (this.xPos - (this.xSize / 2)))
                && (dc.getMouseYPosition() <= (this.yPos + (this.ySize / 2))) && (dc.getMouseYPosition() >= (this.yPos - (this.ySize / 2))));
    }

    public boolean isClicked() {
        return isClicked;
    }

    public int findButton(String name, List<Menu_Button> button) {

        for (int i = 0; i < button.size(); i++) {

            if (button.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public String getName() {
        return this.name;
    }

    public void setState(boolean state) {
        isClicked = state;
    }
    public void disable (){
        enabled = false;
    }
    public void enable (){
        enabled = true;
    }

    public void setGroup(Menu_Button_Group group) {

        this.group = group;
        this.isInGroup = true;

    }

}
