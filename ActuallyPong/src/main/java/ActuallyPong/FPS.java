/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActuallyPong;

/**
 *
 * @author Parker TenBroeck
 */
import DLibX.DConsole;
import java.util.ArrayList;

public class FPS {

    long oldTimeDelay;
    long oldTimeCounter;
    long newTime;
    double currentFPS;
    int totalFrames;
    ArrayList<Double> average = new ArrayList();
    int limit;
    double oldSecondTime;
    int frameCount;
    DConsole dc;

    public FPS(int limit, DConsole dc) {

        this.dc = dc;
        this.limit = limit;
        newTime = System.nanoTime();
        oldTimeDelay = System.nanoTime();
         oldTimeCounter = System.nanoTime();

    }

    @SuppressWarnings("empty-statement")
    double FPS() {
        newTime = System.nanoTime();
        double delta = newTime - oldTimeDelay;  //current loop time 

        double FPS = ((double)1 / (double)(newTime - oldTimeCounter)) * (double)1000000000; //counter calc
        average.add(FPS);
        oldTimeCounter = newTime;
        
        frameCount ++;

        if (System.nanoTime() >= oldSecondTime + 1000000000) { //every second output average FPS 
            oldSecondTime = System.nanoTime();
            currentFPS = (Math.round((averageList() * (double) 100)) / (double) 100);
            average.clear();
          //  System.out.println(frameCount);
            frameCount = 0;
             

        }

        long delay = ((long) ((long) (1000000000 / (long) limit) - (long) delta));
        if (delay > 0) {
           // System.out.println(delay + " " + delta);
            while (newTime + delay >= System.nanoTime());
        }
           //   try{Thread.sleep(10);} catch(Exception e) {} 

        oldTimeDelay = System.nanoTime();;
        return currentFPS;
    }

    public double averageList() {
        double temp = 0;
        for (int i = 0; i < average.size(); i++) {
            temp = temp + average.get(i);
        }
        temp = temp / average.size();
        return temp;
    }
}
