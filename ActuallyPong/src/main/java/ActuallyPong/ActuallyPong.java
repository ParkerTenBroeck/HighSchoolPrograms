 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActuallyPong;

import DLibX.DConsole;
import ActuallyPong.Menu.Menu;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.*;

 public class ActuallyPong {

    public static void main(String[] args) throws InterruptedException, LineUnavailableException, IOException, UnsupportedAudioFileException, FontFormatException {

        DConsole dc = new DConsole(); //makes DConsole

        
         //AudioInputStream audioIn = AudioSystem.getAudioInputStream(ActuallyPong.class.getResourceAsStream("/sound_track/menu_theme_1.wav"));//new File("sound_track/menu_theme_1.wav")); //song
         //Clip clip = AudioSystem.getClip();
         //clip.open(audioIn);
         //clip.start();
         //clip.loop(Clip.LOOP_CONTINUOUSLY);
         
        while (true) {

            int ballCount = 100; //the amount of balls to start with
            int ballsScored = 0; //amount of balls currently scored in match
            int ballSpawnDirection = 1;
            double speed = 1;
            int matchScoreLeft = 0;
            int matchScoreRight = 0;
            int totalScoreLeft = 1;
            int totalScoreRight = 1;
            int freezeZoneSize = 400;
            boolean debug = false;

            Collision collision = new Collision(); //makes collision class

            FPS fps = new FPS(60, dc); // makes a frame counter

            dc.registerFont(ActuallyPong.class.getResourceAsStream("/bit9x9.ttf"));  //registers font used
            RGBcycle RGB = new RGBcycle(0, 0, 255, 1); //creates a new rgb colour  
            //     Doubrle_Press w = new Double_Press(dc, 200, 87);
            //     Double_Press s = new Double_Press(dc, 200, 83);

            Menu menu = new Menu(dc, RGB);
            while (menu.isOpen() == true) {
                menu.run();
                dc.pause(2);
            }

            List<Ball> ball = new ArrayList();  //makes list of balls 

            Paddle[] paddle = new Paddle[]{ //creates paddles 
                new Paddle(dc, ball, collision, 50, 300, 20, 80, 87, 83, 65, 68,
                menu.isGroupOn("t1AI"), menu.getGroupCountUp("t1AI"), menu.getGroupBinary("t1AIPowerChoose"), 0),
                new Paddle(dc, ball, collision, 850, 300, 20, 80, 38, 40, 37, 39,
                menu.isGroupOn("t2AI"), menu.getGroupCountUp("t2AI"), menu.getGroupBinary("t2AIPowerChoose"), 1)}; //makes a left and right paddle

            for (int i = 0; i < ballCount; i++) {
                ball.add(new Ball(dc, collision, 20, 450, 300,
                        randomRange(1, .7) * ballSpawnDirection, randomRange(-1, 1)));
                ballSpawnDirection = ballSpawnDirection * -1;
            }

            dc.setStroke(new BasicStroke(2)); //makes the lins thicker
            dc.setOrigin(DConsole.ORIGIN_CENTER);  //sets origin
            dc.setBackground(Color.black);  //sets background colour to black

            while (!dc.isKeyPressed('r')) {  //main loop of program

                if (dc.getKeyPress(80)) {
                    debug = !debug;
                }

                //   while (dc.isKeyPressed(' ')) {  //when you hold space it gets paused 
                //       dc.pause(20);
                //   }
                //start of RGB controll code 
                RGB.cycle();
            // end of RGB controll code 

                //sets the colour and thickness of lines 
                dc.setPaint(new Color(RGBcycle.rgbR, RGBcycle.rgbG, RGBcycle.rgbB)); //sets paint to new rgb colour 

                dc.drawRect(450, 300, 902, 598); //draws border

                //text code  
                //line code
                dc.setPaint(Color.WHITE);
                dc.setFont(new Font("bit9x9", 120, 120));
                dc.drawString(totalScoreLeft, 300, 300);
                dc.drawString(totalScoreRight, 600, 300);
                dc.setFont(new Font("bit9x9", 40, 40));
                dc.drawString(matchScoreLeft, 300, 220);
                dc.drawString(matchScoreRight, 600, 220);
                dc.setPaint(Color.GRAY);
                dc.fillRect(450, 300, 5, 596);
                dc.setPaint(RGB.color());

                //paddle code
                if (ballCount - matchScoreLeft - matchScoreRight > ball.size()) { //spawns balls 
                    ball.add(new Ball(dc, collision, 20, 450, 300,
                            randomRange(1, .7) * ballSpawnDirection, randomRange(-1, 1)));
                    ballSpawnDirection = ballSpawnDirection * -1;
                }

                for (int i = 0; i < paddle.length; i++) {  //updates and draws all paddles 
                    if (paddle[i].isAI() == false) {   //checks if the paddle has AI
                        paddle[i].move(speed * 3);
                    } else {
                        paddle[i].AI(debug);
                    }

                    paddle[i].draw(debug, i, RGB, freezeZoneSize);  //draws paddle
                }

                //ball code
                for (int i = 0; i < ball.size(); i++) {

                    ball.get(i).draw(debug, i, RGB);  //draws ball
                    if (!dc.isKeyPressed(' ')) {
                        ball.get(i).move(speed);  //moves ball and sets speed

                        if (ball.get(i).checkCollisionWall(910, -10, 600, 0) == true) { //checks if ball has hit wall
                            ball.get(i).collision();  //slows ball down from collision
                        }
                        for (int o = 0; o < paddle.length; o++) {   //checks if ball has hit any paddle 

                            ball.get(i).checkIfFreeze(paddle[o], freezeZoneSize);

                            if (ball.get(i).checkCollisionRect(paddle[o].xPos(), paddle[o].yPos(),
                                    paddle[o].sizeX(), paddle[o].sizeY(), paddle[o].slap()) == true) {
                                ball.get(i).collision(); //slows ball down from collision
                            }
                        }
                        if (ball.get(i).isScoreOnLeft(0) == true) { //checks for left score
                            ball.remove(i);
                            ballsScored++;
                            i--;
                            matchScoreRight++;
                        } else if (ball.get(i).isScoreOnRight(900) == true) { //checks for left score
                            ball.remove(i);
                            ballsScored++;
                            i--;
                            matchScoreLeft++;
                        }
                    }
                }
                if (ball.isEmpty()) {         //checks if there are no more balls 
                    ballsScored = 0;
                    if (matchScoreLeft > matchScoreRight) {
                        totalScoreLeft++;
                        matchScoreRight = 0;
                        matchScoreLeft = 0;
                    } else if (matchScoreRight > matchScoreLeft) {
                        totalScoreRight++;
                        matchScoreRight = 0;
                        matchScoreLeft = 0;
                    } else {
                        matchScoreRight = 0;
                        matchScoreLeft = 0;
                    }
                    ballCount = totalScoreLeft * totalScoreRight;
                }

                dc.redraw();
                dc.clear();

                dc.setPaint(Color.WHITE);
                dc.setFont(new Font("bit9x9", 40, 40));
                dc.drawString(fps.FPS(), 450, 30);
                dc.setPaint(RGB.color());

            }

        }
    }

    public static double randomRange(double min, double max) {
        double x = (double) ((Math.random() * ((max - min) + 1)) + min);
        return x;
    }

}
