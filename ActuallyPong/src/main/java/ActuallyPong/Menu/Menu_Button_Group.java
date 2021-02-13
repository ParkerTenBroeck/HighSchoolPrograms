/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActuallyPong.Menu;

import java.util.List;

/**
 *
 * @author shelt
 */
public class Menu_Button_Group {

    String buttonNames[];
    List<Menu_Button> button;
    String name;

    public Menu_Button_Group(String name, int type, List<Menu_Button> button, String buttonNames[]) {

        this.buttonNames = buttonNames;
        this.button = button;
        this.name = name;

        if (type == 1) {

            for (String buttonName : buttonNames) {
                setThisGroup(buttonName);
            }

        } else if (type == 2) {

        }
    }

    public void clear() {
        for (String buttonName : buttonNames) {
            setButton(buttonName, false);
        }

    }

    public boolean isGroupOn() {
        for (String buttonName : buttonNames) {
            if (isClicked(buttonName)) {
                return true;
            }
        }
        return false;
    }

    public void setButton(String name, boolean state) {
        button.get(button.get(0).findButton(name, button)).setState(state);
    }

    public void disable() {

        for (String buttonName : buttonNames) {
            button.get(button.get(0).findButton(buttonName, button)).disable();
        }
    }

    public void enable() {

        for (String buttonName : buttonNames) {
            button.get(button.get(0).findButton(buttonName, button)).enable();
        }
    }

    public void setGroup(boolean state) {
        for (String buttonName : buttonNames) {
            button.get(button.get(0).findButton(buttonName, button)).setState(state);
        }
    }

    public boolean isClicked(String name) {
        return button.get(button.get(0).findButton(name, button)).isClicked();
    }

    public void setThisGroup(String name) {
        button.get(button.get(0).findButton(name, button)).setGroup(this);
    }

    public String getName() {
        return this.name;
    }

    public int getGroupCountUp() {
        for (int i = 0; i < this.buttonNames.length; i++) {
            if (isClicked(this.buttonNames[i])) {
                return i + 1;
            }
        }
        return -1;
    }
    public int getGroupBinary (){
        int temp = 0;
        for(int i = 0; i < this.buttonNames.length ; i++){
            if (isClicked(this.buttonNames[i])) {
                temp = temp + (int)Math.pow(2, i);
                // System.out.println(i);
            }
        }
        return temp;
    }
}
