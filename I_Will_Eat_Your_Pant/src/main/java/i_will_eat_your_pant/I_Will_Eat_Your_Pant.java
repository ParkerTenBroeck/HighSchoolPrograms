/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i_will_eat_your_pant;

import com.sun.scenario.Settings;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Parker TenBroeck
 */
public class I_Will_Eat_Your_Pant {

    static BufferedImage image;
    Clip clip = null;

    public I_Will_Eat_Your_Pant() {

        image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        Rectangle rect = new Rectangle(640, 480);
        Font font = new Font("Comic Sans MS", Font.PLAIN, 60);

        JFrame window = new JFrame();
        window.setTitle("I Will Eat Your Pant");

        window.setLayout(new BorderLayout());
        //window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel(new ImageIcon(image));
        topPanel.add(label);

        window.add(topPanel, BorderLayout.CENTER);
        window.setVisible(true);
        window.pack();

        Graphics2D gr = image.createGraphics();

        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                window.setVisible(false); //you can't see me!
                clip.stop();
                try {
                    Thread.sleep(60000);
                } catch (Exception er) {

                }
                FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                //int volume = Settings.getSettings().getVolume();
                float min = control.getMinimum();
                float max = control.getMaximum();
                control.setValue(min);
                
                clip.setFramePosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();

                for (float i = -40; i <= max; i += 0.003) {
                    control.setValue(i);
                    try {
                        Thread.sleep(1);
                    } catch (Exception er) {

                    }
                }
            }

        });

        URL i_will_eat_your_pant;

        i_will_eat_your_pant = this.getClass().getResource("/i_will_eat_your_pant.wav");

        try {
            AudioInputStream sound = null;
            sound = AudioSystem.getAudioInputStream(i_will_eat_your_pant);

            clip = AudioSystem.getClip();
            clip.open(sound);
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            System.out.println("ff");
        }

        float i = 0;
        while (true) {
            i += 0.0007;
            if (i > 1) {
                i = 0;
            }
            gr.setPaint(Color.getHSBColor(i, 1, 1));
            gr.fillRect(0, 0, 640, 480);
            Color c = gr.getColor();
            gr.setPaint(new Color((~c.getRed()) & 0xff, (~c.getGreen()) & 0xff, (~c.getBlue()) & 0xff));
            drawCenteredString(gr, "I Will Eat Your Pant", rect, font);
            topPanel.repaint();
            try {
                Thread.sleep(3);
            } catch (Exception e) {

            }
        }
    }

    public static void main(String[] args) {
        I_Will_Eat_Your_Pant I_Will_Eat_Your_Pant = new I_Will_Eat_Your_Pant();
    }

    static public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }
}
