/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroides;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Parker TenBroeck
 */
public class Texture {

    public ArrayList<Image[]> asteroidTextures = new ArrayList();
    public ArrayList<Image[]> shipTextures = new ArrayList();
    public ArrayList<Image[]> SaucerTextures = new ArrayList();
    public Image lifeLogo;

    int lastIndex;

    public Texture() {
        asteroidTextures.add(loadSize("/asteroidImages/parkerT.png"));
        asteroidTextures.add(loadSize("/asteroidImages/parkerM.png"));
        asteroidTextures.add(loadSize("/asteroidImages/kristine.png"));
        asteroidTextures.add(loadSize("/asteroidImages/danielle.png"));
        asteroidTextures.add(loadSize("/asteroidImages/meghan.png"));
        lifeLogo = load("/lifeIcon.png");
        lifeLogo = lifeLogo.getScaledInstance(28, 34, Image.SCALE_DEFAULT);
    }

    private Image[] loadSize(String loc) {

        BufferedImage in = null;
        try {
            in = ImageIO.read(getClass().getResourceAsStream(loc));
        } catch (IOException ex) {
            Logger.getLogger(Asteroid.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image image1 = in.getScaledInstance(38, 38, Image.SCALE_DEFAULT);
        Image image2 = in.getScaledInstance(80, 80, Image.SCALE_DEFAULT);
        Image image3 = in.getScaledInstance(115, 115, Image.SCALE_DEFAULT);

        return new Image[]{image1, image2, image3};
    }

    private BufferedImage load(String loc) {

        BufferedImage in = null;
        try {
            in = ImageIO.read(getClass().getResourceAsStream(loc));//new File(loc));
        } catch (IOException ex) {
            Logger.getLogger(Asteroid.class.getName()).log(Level.SEVERE, null, ex);
        }

        return in;
    }

    public Image getRandomAsteroidTexture(int size) {

        if (size >= 3) {
            return null;
        }

        lastIndex = (int) randomRange(0, asteroidTextures.size() - 1);

        return asteroidTextures.get(lastIndex)[size];
    }

    public static double randomRange(double min, double max) {
        double x = (double) ((Math.random() * ((max - min) + 1)) + min);
        return x;
    }

    public int getIndex() {
        return lastIndex;
    }

    public Image getImage(int index, int size) {
        lastIndex = index;
        if (size >= 2) {
            return null;
        }
        return asteroidTextures.get(index)[size];
    }
}
