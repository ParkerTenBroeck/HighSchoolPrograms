/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dvd_bouncing_logo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Parker TenBroeck
 */
public class Dvd_bouncing_logo {

    JFrame frame;

    ImageIcon image;
    BufferedImage buffImage;

    int minX, minY, maxX, maxY;

    int xPos = 0, yPos = 0, xAcc = 1, yAcc = 1;

    public static void main(String[] args) {

        Dvd_bouncing_logo main = new Dvd_bouncing_logo();

    }

    public Dvd_bouncing_logo() {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        maxX = (int) dim.getWidth();
        maxY = (int) dim.getHeight();
        minX = 0;
        minY = 0;

        frame = new JFrame();
        frame.setTitle("dvd");

        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        frame.add(topPanel);

        URL url;
        url = this.getClass().getResource("/dvd.png");
        image = new ImageIcon(url);
        double scale = 0.20;
        image.setImage(image.getImage().getScaledInstance((int) (image.getIconWidth() * scale),
                (int) (image.getIconHeight() * scale), Image.SCALE_SMOOTH));
        buffImage = new BufferedImage(image.getIconWidth(),
                image.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = buffImage.createGraphics();
        g.drawImage(image.getImage(), 0, 0, null);
        g.dispose();

        image.setImage(colorShift());
        JLabel label = new JLabel(image);
        topPanel.add(label);

        topPanel.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
        frame.getContentPane().setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
        frame.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));

        frame.pack();

        frame.setVisible(true);

        while (true) {
            xPos += xAcc * 2;
            yPos += yAcc * 2;
            frame.setLocation(xPos, yPos);
            image.setImage(colorShift());
            frame.repaint();
            checkCollision();

            try {
                Thread.sleep(10);
            } catch (Exception e) {

            }
        }
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("keyPressed");
    }

    public void checkCollision() {

        double imageW = image.getIconWidth();
        double imageH = image.getIconHeight();

        if (xPos < 0) {
            xAcc = 1;
        }
        if (xPos + imageW > maxX) {
            xAcc = -1;
        }
        if (yPos < 0) {
            yAcc = 1;
        }
        if (yPos + imageH > maxY) {
            yAcc = -1;
        }
    }

    private BufferedImage colorShift() {

        int width = buffImage.getWidth();
        int height = buffImage.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {

                if (buffImage.getRGB(xx, yy) >> 24 != 0x00) {
                    float val = (float) ((System.currentTimeMillis() / 10000.0)
                            - Math.floor(System.currentTimeMillis() / 10000.0));
                    Color color = Color.getHSBColor(val, 1, 1);
                    color = new Color(color.getRed(), color.getGreen(),
                            color.getBlue(), buffImage.getRGB(xx, yy) >> 24 & 0xFF);
                    buffImage.setRGB(xx, yy, color.getRGB());

                }
            }
        }
        return buffImage;
    }

}
