/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dtest;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.Font;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
//import javax.swing.JFrame;

public class Main /*extends JFrame*/ implements Runnable {

    private static final long serialVersionUID = 1L;
    public int mapWidth = 15;
    public int mapHeight = 15;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;
    ArrayList<object> objs = new ArrayList();
    DConsole dc = new DConsole();

    Timer timer = new Timer();
    int deltaFPS = 0;
    int deltaUPS = 0;
    int FPS = 0;
    int UPS = 0;

    Camera cam = new Camera(0, 0, -200, 0, 0, 0, 450, 300, dc); //making the camera

    //File f = new File("obj/Mario/Mario.obj");
    //File f = new File("obj/GLADoS/GLADoS.obj");
	File f = new File("obj/Mario/Mario.obj");
    object Mario = OBJLoader.loadModle(f, 0, 0, 0, degToRad(180), 0, 0, 0.5);
    

    public Main() throws IOException {
        thread = new Thread(this);
        image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB); // creates a new image
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData(); // all pixles in display 

        //setSize(640, 480);
        //setResizable(false);
        //setTitle("3D Test");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBackground(Color.BLACK);
        //setLocationRelativeTo(null);
        //setVisible(true);

        dc.registerFont(Main.class.getResourceAsStream("/bit9x9.ttf"));
        dc.setOrigin(DConsole.ORIGIN_CENTER);
        dc.setBackground(Color.LIGHT_GRAY);

        objs.add(Mario);
        
        FPSUPS(1);
        updateGame(60);
        start();
    }

    private synchronized void start() {
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        final int TICKS_PER_SECOND = 60;
        final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
        final int MAX_FRAMESKIP = 60;

        double next_game_tick = System.currentTimeMillis();
        int loops;

        while (running) {

            loops = 0;
            while (System.currentTimeMillis() > next_game_tick
                    && loops < MAX_FRAMESKIP) {

                next_game_tick += SKIP_TICKS;
                loops++;
            }
            render();
            deltaFPS++;
        }
    }

    class updateGame extends TimerTask {

        @Override
        public void run() {
            update();
            deltaUPS++;
            updateGame(60);
        }
    }

    class FPSUPS extends TimerTask {

        @Override
        public void run() {
            FPS = deltaFPS;
            UPS = deltaUPS;
            deltaFPS = 0;
            deltaUPS = 0;
            FPSUPS(1);
        }
    }

    public void FPSUPS(int seconds) {
        
        timer.schedule(new FPSUPS(), seconds * 1000);
    }

    public void updateGame(int timesPerSecond) {

        timer.schedule(new updateGame(), 1000 / timesPerSecond);
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main();//starts a new main 
    }

    public void render() {

         cam.drawPOV(objs); //renders new items on screen 
        
        dc.setOrigin(DConsole.ORIGIN_LEFT);
        dc.setPaint(Color.BLACK);
        dc.setFont(new Font("bit9x9", 20, 20));
        dc.drawString("cam xPos " + roundToDecimal(cam.xPos, 2), 10, 20);
        dc.drawString("cam yPos " + roundToDecimal(cam.yPos, 2), 10, 35);
        dc.drawString("cam zPos " + roundToDecimal(cam.zPos, 2), 10, 50);
        dc.drawString("screen xPos " + roundToDecimal(cam.xPos, 2), 10, 65);
        dc.drawString("screen yPos " + roundToDecimal(cam.yPos, 2), 10, 80);
        dc.drawString("screen zPos " + roundToDecimal((cam.zPos + cam.distance), 2), 10, 95);
        dc.drawString("rotation X " + roundToDecimal(cam.xRot / Math.PI * 180, 2), 10, 110);
        dc.drawString("rotation Y " + roundToDecimal(cam.yRot / Math.PI * 180, 2), 10, 125);
        dc.drawString("rotation Z " + roundToDecimal(cam.zRot / Math.PI * 180, 2), 10, 140);
        dc.drawString("FPS " + FPS, 10, 155);
        dc.drawString("UPS " + UPS, 10, 170);
        dc.setOrigin(DConsole.ORIGIN_CENTER);
    }

    public void update() {
        if (dc.isKeyPressed('w')) {
            cam.xPos += sin(cam.yRot);
            cam.zPos += cos(cam.yRot);
        }
        if (dc.isKeyPressed('s')) {
            cam.xPos -= sin(cam.yRot);
            cam.zPos -= cos(cam.yRot);
        }
        if (dc.isKeyPressed('d')) {
            cam.xPos += cos(cam.yRot);
            cam.zPos += sin(-cam.yRot);
        }
        if (dc.isKeyPressed('a')) {
            cam.xPos -= cos(cam.yRot);
            cam.zPos -= sin(-cam.yRot);
        }
        if (dc.isKeyPressed(32)) {
            cam.yPos -= 1;
        }
        if (dc.isKeyPressed(16)) {
            cam.yPos += 1;
        }

        if (dc.isKeyPressed('z')) {
            cam.zoom = 5;
        } else {
            cam.zoom = 1;
        }

        if (dc.isKeyPressed(37)) {
            cam.yRot -= 0.02;
        } else if (dc.isKeyPressed(39)) {
            cam.yRot += 0.02;
        }
        if (dc.isKeyPressed(38)) {
            cam.xRot += 0.02;
        } else if (dc.isKeyPressed(40)) {
            cam.xRot -= 0.02;
        }
        if (dc.isKeyPressed('u')) {
            cam.zRot += 0.02;
        } else if (dc.isKeyPressed('o')) {
            cam.zRot -= 0.02;
        }
        if (dc.isKeyPressed('r')) {
            cam.xRot = 0;
            cam.zRot = 0;
        }
        Mario.yRotate(0.003);
    }

    static public double roundToDecimal(double r, int d) {
        int temp = (int) Math.pow(10, d);
        return (double) Math.round(r * temp) / temp;
    }

    static double degToRad(double angle) {
        return angle * Math.PI / 180;
    }
}
