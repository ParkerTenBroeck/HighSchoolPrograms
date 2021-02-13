/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroides;

/**
 *
 * @author shelt
 */
public class GameTimer {

    private long start = 0;
    private long delay = 0;
    private GameTick currentTick = null;
    private String name;

    public GameTimer(String name, long delay, GameTick currentTick) {
        
        this.delay = delay;
        this.name = name;
        this.currentTick = currentTick;
        this.start = currentTick.value();

    }

    public String getName() {
        return name;
    }

    public boolean isExpired() {
        return (this.currentTick.value() - this.start) >= this.delay;
    }

}
