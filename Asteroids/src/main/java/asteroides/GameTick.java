/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroides;

/**
 *
 * @author Parker TenBroeck
 */
public class GameTick {

    private long value;

    public GameTick(long value) {
        this.value = value;
    }
    public void inc(){
        value++;
    }
    public long value(){
        return value;
    }
    public void zero(){
        value = 0;
    }
    

}
