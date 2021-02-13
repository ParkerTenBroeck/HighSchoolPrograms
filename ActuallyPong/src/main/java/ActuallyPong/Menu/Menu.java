/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActuallyPong.Menu;

import DLibX.DConsole;
import ActuallyPong.RGBcycle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tenbroep
 */
public class Menu {

    static final Image slapImage = new javax.swing.ImageIcon(Menu.class.getResource("/images/slap.png")).getImage();
    static final Image dashImage = new javax.swing.ImageIcon(Menu.class.getResource("/images/dash.png")).getImage();
    static final Image freezeImage = new javax.swing.ImageIcon(Menu.class.getResource("/images/freeze.png")).getImage();

    DConsole dc;
    RGBcycle RGB;
    List<Menu_Button> button = new ArrayList();
    List<Menu_Text> text = new ArrayList();
    List<Menu_Button_Group> group = new ArrayList();
    boolean start = false;

    public Menu(DConsole dc, RGBcycle RGB) {

        dc.setOrigin(DConsole.ORIGIN_CENTER);
        dc.setBackground(Color.black);
        this.dc = dc;
        this.RGB = RGB;

        text.add(new Menu_Text("Pong", 450, 50, 120, Color.WHITE, DConsole.ORIGIN_CENTER));
        
   //     text.add(new Menu_Text("Debug", 830, 50, 55, Color.DARK_GRAY, DConsole.ORIGIN_RIGHT));
   //     button.add(new Menu_Button("Debug", 725, 50, 175, 50, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, Color.WHITE));

        text.add(new Menu_Text("Team 1", 70, 110, 60, Color.LIGHT_GRAY, DConsole.ORIGIN_LEFT));
        text.add(new Menu_Text("Player", 70, 160, 60, RGB, DConsole.ORIGIN_LEFT));
        button.add(new Menu_Button("t1PlayerButton", 30, 160, 30, 30, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, RGB));
        setButton("t1PlayerButton", true);
        text.add(new Menu_Text("Easy", 70, 210, 60, Color.GREEN, DConsole.ORIGIN_LEFT));
        button.add(new Menu_Button("t1EasyButton", 30, 210, 30, 30, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, Color.GREEN));
        text.add(new Menu_Text("Normal", 70, 260, 60, Color.YELLOW, DConsole.ORIGIN_LEFT));
        button.add(new Menu_Button("t1NormalButton", 30, 260, 30, 30, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, Color.YELLOW));
        text.add(new Menu_Text("Hard", 70, 310, 60, Color.RED, DConsole.ORIGIN_LEFT));
        button.add(new Menu_Button("t1HardButton", 30, 310, 30, 30, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, Color.RED));
        text.add(new Menu_Text("hopeless", 70, 360, 60, Color.DARK_GRAY, DConsole.ORIGIN_LEFT));
        button.add(new Menu_Button("t1HopelessButton", 30, 360, 30, 30, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, Color.DARK_GRAY));
        group.add(new Menu_Button_Group("t1All", 1, button, new String[]{"t1PlayerButton", "t1EasyButton", "t1NormalButton", "t1HardButton", "t1HopelessButton"}));
        group.add(new Menu_Button_Group("t1AI", 2, button, new String[]{"t1EasyButton", "t1NormalButton", "t1HardButton", "t1HopelessButton"}));

        text.add(new Menu_Text("Team 2", 830, 110, 60, Color.LIGHT_GRAY, DConsole.ORIGIN_RIGHT));
        text.add(new Menu_Text("Player", 830, 160, 60, RGB, DConsole.ORIGIN_RIGHT));
        button.add(new Menu_Button("t2PlayerButton", 870, 160, 30, 30, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, RGB));
        setButton("t2PlayerButton", true);
        text.add(new Menu_Text("Easy", 830, 210, 60, Color.GREEN, DConsole.ORIGIN_RIGHT));
        button.add(new Menu_Button("t2EasyButton", 870, 210, 30, 30, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, Color.GREEN));
        text.add(new Menu_Text("Normal", 830, 260, 60, Color.YELLOW, DConsole.ORIGIN_RIGHT));
        button.add(new Menu_Button("t2NormalButton", 870, 260, 30, 30, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, Color.YELLOW));
        text.add(new Menu_Text("Hard", 830, 310, 60, Color.RED, DConsole.ORIGIN_RIGHT));
        button.add(new Menu_Button("t2HardButton", 870, 310, 30, 30, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, Color.RED));
        text.add(new Menu_Text("hopeless", 830, 360, 60, Color.DARK_GRAY, DConsole.ORIGIN_RIGHT));
        button.add(new Menu_Button("t2HopelessButton", 870, 360, 30, 30, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, Color.DARK_GRAY));
        group.add(new Menu_Button_Group("t2All", 1, button, new String[]{"t2PlayerButton", "t2EasyButton", "t2NormalButton", "t2HardButton", "t2HopelessButton"}));
        group.add(new Menu_Button_Group("t2AI", 2, button, new String[]{"t2EasyButton", "t2NormalButton", "t2HardButton", "t2HopelessButton"}));

        text.add(new Menu_Text("Slap", 150, 410, 30, Color.WHITE, DConsole.ORIGIN_LEFT));
        button.add(new Menu_Button("t1PowerSlap", 70, 410, 30, 30, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, Color.RED));
        text.add(new Menu_Text("Dash", 150, 460, 30, Color.WHITE, DConsole.ORIGIN_LEFT));
        button.add(new Menu_Button("t1PowerDash", 70, 460, 30, 30, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, Color.GREEN));
        text.add(new Menu_Text("Freeze", 150, 510, 30, Color.WHITE, DConsole.ORIGIN_LEFT));
        button.add(new Menu_Button("t1PowerFreeze", 70, 510, 30, 30, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, Color.BLUE));
        group.add(new Menu_Button_Group("t1AIPowerChoose", 2, button, new String[]{"t1PowerSlap", "t1PowerDash", "t1PowerFreeze"}));
        setGroup("t1AIPowerChoose", true);

        text.add(new Menu_Text("Slap", 750, 410, 30, Color.WHITE, DConsole.ORIGIN_RIGHT));
        button.add(new Menu_Button("t2PowerSlap", 830, 410, 30, 30, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, Color.RED));
        text.add(new Menu_Text("Dash", 750, 460, 30, Color.WHITE, DConsole.ORIGIN_RIGHT));
        button.add(new Menu_Button("t2PowerDash", 830, 460, 30, 30, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, Color.GREEN));
        text.add(new Menu_Text("Freeze", 750, 510, 30, Color.WHITE, DConsole.ORIGIN_RIGHT));
        button.add(new Menu_Button("t2PowerFreeze", 830, 510, 30, 30, 5, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, Color.BLUE));
        group.add(new Menu_Button_Group("t2AIPowerChoose", 2, button, new String[]{"t2PowerSlap", "t2PowerDash", "t2PowerFreeze"}));
        setGroup("t2AIPowerChoose", true);

        text.add(new Menu_Text("Start", 450, 549, 70, Color.WHITE, DConsole.ORIGIN_CENTER));
        button.add(new Menu_Button("startButton", 450, 550, 300, 50, 5, Color.WHITE, Color.BLACK, RGB, Color.WHITE));

    }

    public void run() {  //draws and carrys out the functions of a menu

        RGB.cycle();

        button.stream().forEach((buttonf) -> {
            buttonf.draw(dc);

        });
        text.stream().forEach((textf) -> {
            textf.draw(dc);
        });

        if (isGroupOn("t1AI")) {
            enableGroup("t1AIPowerChoose");
            dc.setOrigin(DConsole.ORIGIN_CENTER);
            dc.drawImage(slapImage, 120, 410);
            dc.drawImage(dashImage, 120, 460);
            dc.drawImage(freezeImage, 120, 510);

        } else {
            disableGroup("t1AIPowerChoose");
            dc.setPaint(Color.BLACK);
            dc.fillRect(212.5, 460, 125, 120);
        }
        if (isGroupOn("t2AI")) {
            enableGroup("t2AIPowerChoose");
            dc.setOrigin(DConsole.ORIGIN_CENTER);
            dc.drawImage(slapImage, 780, 410);
            dc.drawImage(dashImage, 780, 460);
            dc.drawImage(freezeImage, 780, 510);
        } else {
            disableGroup("t2AIPowerChoose");
            dc.setPaint(Color.BLACK);
            dc.fillRect(685, 460, 130, 120);
        }

        dc.redraw();
        dc.clear();

    }

    public boolean isOpen() {

        return !isClicked("startButton");
    }

    public boolean isClicked(String name) {
        return button.get(button.get(0).findButton(name, button)).isClicked();
    }

    public void setButton(String name, boolean state) {
        button.get(button.get(0).findButton(name, button)).setState(state);
    }

    public int getGroupCountUp(String name) {   //gives a number starting at 1 then going up for each button untill one is pressed
        return group.get(findGroup(name)).getGroupCountUp();
    }

    public int getGroupBinary(String name) {  //each button is added to a binary total dependent on order
          return group.get(findGroup(name)).getGroupBinary();  
    }

    public void setGroup(String name, boolean state) {
        group.get(findGroup(name)).setGroup(state);
    }

    public void disableGroup(String name) {
        group.get(findGroup(name)).disable();
    }

    public void enableGroup(String name) {
        group.get(findGroup(name)).enable();
    }

    public boolean isGroupOn(String name) {
        return group.get(findGroup(name)).isGroupOn();
    }

    public int findGroup(String name) {

        for (int i = 0; i < group.size(); i++) {

            if (group.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }
}
