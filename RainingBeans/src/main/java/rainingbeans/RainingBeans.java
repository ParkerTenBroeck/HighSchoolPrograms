/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rainingbeans;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Parker TenBroeck
 */
public class RainingBeans {

    int maxX;
    int maxY;
    JFrame frame;
    URL beanURL;
    BufferedImage beanImage;
    BufferedImage CLEAR;
    int beanCount;
    ArrayList<Bean> beans = new ArrayList();

    public static void main(String[] args) {

        RainingBeans bean = new RainingBeans();
    }

    public RainingBeans() {

        frame = new JFrame();
        frame.setTitle("beans");

        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        frame.add(topPanel);

        topPanel.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
        frame.getContentPane().setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
        frame.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));

        beanURL = this.getClass().getResource("/bean.png");

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        maxX = (int) dim.getWidth() + 10;
        maxY = (int) dim.getHeight() + 10;

        BufferedImage image = new BufferedImage(maxX, maxY, BufferedImage.TYPE_4BYTE_ABGR);
        CLEAR = new BufferedImage(maxX, maxY, BufferedImage.TYPE_4BYTE_ABGR);

        try {
            beanImage = ImageIO.read(beanURL);
        } catch (IOException ex) {
            Logger.getLogger(RainingBeans.class.getName()).log(Level.SEVERE, null, ex);
        }

        JLabel label = new JLabel(new ImageIcon(image));
        topPanel.add(label);

        frame.pack();
        frame.setVisible(true);

        try {
            beanCount = Integer.valueOf(JOptionPane.showInputDialog(frame,
                    "how many beans my friend?", null));
        } catch (Exception e) {
            System.exit(0);
        }

        for (int i = 0; i < beanCount; i++) {
            beans.add(new Bean(dim, beanImage));
        }

        Graphics g = image.getGraphics();

        Timer timer = new Timer(30, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                image.setData(CLEAR.getRaster());

                for (Bean bean : beans) {
                    BufferedImage temp = bean.getImage();
                    g.drawImage(temp, bean.getMX(), bean.getMY(), null);
                    bean.update();
                }

                frame.repaint();
            }
        });
        timer.start();

    }

}
