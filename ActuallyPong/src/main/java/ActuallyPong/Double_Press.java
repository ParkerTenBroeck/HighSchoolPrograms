/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActuallyPong;

import DLibX.DConsole;

/**
 *
 * @author tenbroep
 */
public class Double_Press {

    int maxDelay;
    int currentDelay;
    int keyCode;
    DConsole dc;
   boolean keyState;
    
    public Double_Press(DConsole dc, int maxDelay, int keyCode) {

        this.maxDelay = maxDelay;
        this.keyCode = keyCode;
        this.dc = dc;

    }

    public boolean isDoublePressed() {

        if (this.currentDelay > 0) {
            this.currentDelay--;
        }
        if (getKeyPress()) {

            if (this.currentDelay > 0) {
                this.currentDelay = 0;
                return true;
            } else if (this.currentDelay == 0) {
                this.currentDelay = this.maxDelay;
                
                return false;
            } else {
                
            }
        }
        return false;
    }

    

    public boolean getKeyPress() {

        if (dc.isKeyPressed(keyCode) && keyState == false) {
            keyState = true;
            return true;
        } else if (!dc.isKeyPressed(keyCode)) {
            keyState = false;
            return false;
        } else {
            return false;
        }
        
    }

}
