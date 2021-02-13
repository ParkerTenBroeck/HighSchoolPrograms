/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroides;

import DLibX.DConsole;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Parker TenBroeck
 */
public class Collision {

    public Collision() {

    }

    public Bullet bulletInHitZones(ArrayList<double[]> hitZones, ArrayList<Bullet> bullets, DConsole dc) {

        for (double[] circle : hitZones) {

            for (Bullet bullet : bullets) {
                if (isPointInCircle(circle[0] / 2, circle[1],
                        circle[2], bullet.getXPos(), bullet.getYPos())) {

                    return bullet;
                }
            }
        }
        return null;
    }

    public boolean bulletInAsteroid(Asteroid asteroid, Bullet bullet, DConsole dc) {

        ArrayList<double[]> hitZones = asteroid.getHitZones(dc);

        for (double[] circle : hitZones) {

            if (isPointInCircle(circle[0] / 2, circle[1],
                    circle[2], bullet.getXPos(), bullet.getYPos())) {

                return true;
            }
        }
        return false;
    }

    public boolean isPointInCircle(double CircleSize, double circleX,
            double circleY, double pointX, double pointY) {

        double xChange = circleX - pointX;
        double yChange = circleY - pointY;

        return magnitude(xChange, yChange) <= CircleSize;

    }

    public double magnitude(double xChange, double yChange) {
        return Math.sqrt((xChange * xChange) + (yChange * yChange));
    }

    public boolean arePointsInAsteroid(Asteroid asteroid, double[][] points, DConsole dc) {

        ArrayList<double[]> hitZones = asteroid.getHitZones(dc);

        return arePointsInHitZone(hitZones, points, dc);
    }

    public boolean arePointsInHitZone(ArrayList<double[]> hitZones, double[][] points, DConsole dc) {

        for (int i = 0; i < points[0].length; i++) {

            for (double[] circle : hitZones) {

                if (isPointInCircle(circle[0] / 2, circle[1], circle[2], points[0][i], points[1][i])) {

                    return true;
                }

            }
        }
        return false;
    }

    public boolean arePointsInHitZone(ArrayList<double[]> hitZones,
            ArrayList<double[][]> points, DConsole dc) {

        for (double[][] p : points) {
            if (arePointsInHitZone(hitZones, p, dc)) {
                return true;
            }
        }
        return false;
    }

    public boolean arePointsInAsteroids(ArrayList<Asteroid> a,
            ArrayList<double[][]> points, DConsole dc) {

        for (int i = 0; i < a.size(); i++) {

            for (double[][] p : points) {
                if (arePointsInAsteroid(a.get(i), p, dc)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param a
     * @param points
     * @param dc
     * @return
     */
    public Asteroid asteroidOfCollision(ArrayList<Asteroid> a,
            ArrayList<double[][]> points, DConsole dc) {

        for (int i = 0; i < a.size(); i++) {

            for (double[][] p : points) {
                if (arePointsInAsteroid(a.get(i), p, dc)) {
                    return a.get(i);
                }
            }
        }
        return null;
    }

}
