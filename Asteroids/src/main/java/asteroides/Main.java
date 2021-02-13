/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroides;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Parker TenBroeck
 */
public class Main implements Runnable {

    private Thread thread;
    private boolean running;
    Timer timer = new Timer();
    int deltaFPS = 0;
    int deltaUPS = 0;
    int FPS = 0;
    int UPS = 0;
    GameTick gameTick = new GameTick(0);
    DConsole dc = new DConsole();
    Collision collision = new Collision();
    Texture texture = new Texture();

    int lifes = 3, score, level = 0;

    boolean p = false;
    boolean invince = false;
    boolean debug = false;

    Ship ship = new Ship(dc.getWidth() / 2, dc.getHeight() / 2);
    Saucer saucer = new Saucer(1, dc);

    ArrayList<Asteroid> asteroids = new ArrayList();
    ArrayList<Bullet> bullets = new ArrayList();
    ArrayList<Particle> particles = new ArrayList();
    ArrayList<GameTimer> gameTimers = new ArrayList();

    public static void main(String[] args) {

        Main asteroides = new Main();

    }

    public Main() {

        addAsteroids(4 + level);
        //asteroids.add(new Asteroid(10, 10, 20, texture));
        
        thread = new Thread(this);
        dc.setBackground(Color.black);
        dc.registerFont(getClass().getResourceAsStream("/bit9x9.ttf"));
        FPSUPS(1);
        start();
    }

    public void render() {

        dc.setOrigin(DConsole.ORIGIN_CENTER);
        dc.setPaint(Color.WHITE);

        for (Asteroid a : asteroids) {
            a.draw(dc, p);
        }
        for (Particle p : particles) {
            p.draw(dc);
        }
        for (Bullet b : bullets) {
            b.draw(dc);
        }

        if (saucer != null) {
            saucer.draw(dc);
        }

        if (debug) {
            dc.setPaint(Color.BLUE);
            drawHitBoxes();
            dc.setPaint(Color.WHITE);
        }

        if (invince) {
            ship.draw(dc, getRGBfromNumber(gameTick.value()));
        } else {
            ship.draw(dc);
        }

        dc.setFont(new Font("bit9x9", 40, 40));
        dc.setOrigin(DConsole.ORIGIN_LEFT);
        dc.drawString(score, 30, 30);
        dc.setPaint(Color.WHITE);
        dc.setOrigin(DConsole.ORIGIN_CENTER);
        if (debug) {
            dc.drawString(FPS, dc.getWidth() / 2, 30);
            dc.drawString(gameTick.value(), dc.getWidth() / 2, 60);
        }

        drawLifes();

        dc.redraw();
        dc.clear();
    }

    public void update() {

        ship.updateMomentum(dc);
        userInput();

        allAsteroidCollision();
        updateParticles();
        updateBullets();

        if (asteroids.isEmpty()) {
            makeTimer("spawnAsteroids", 60);
        }
        if (timerIsExpired("spawnAsteroids")) {
            level++;
            addAsteroids(4 + level);
        }
        if (!invince) {
            this.timerIsExpired("invincibility");
            if (!timerExists("invincibility")) {
                if (allShipCollision()) {
                    this.addParticles(ship.getXPos(), ship.getYPos(), 10);
                    ship = new Ship(dc.getWidth() / 2, dc.getHeight() / 2);
                    lifes--;
                    makeTimer("invincibility", 100);
                }
                ship.setVisibility(true);
            } else {
                if (timerIsExpired("blink")) {
                    ship.invertVisibility();
                }

                makeTimer("blink", 10);
            }
        } else {
            ship.setVisibility(true);
        }

        if (saucer != null) {
             if (allSaucerCollision()) {     
                this.addParticles(saucer.getXPos(), saucer.getYPos(), 10);
                saucer = null;
            }
        }
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

        final long TICKS_PER_SECOND = 60;
        final long SKIP_TICKS = 1000000000 / TICKS_PER_SECOND;
        final long MAX_FRAMESKIP = 60;

        double next_game_tick = System.nanoTime();
        int loops;

        update(); //updates the game on gametick zero

        while (running) {

            loops = 0;
            while (System.nanoTime() > next_game_tick
                    && loops < MAX_FRAMESKIP) {

                gameTick.inc();
                update();
                deltaUPS++;
                next_game_tick += SKIP_TICKS;
                loops++;
            }
            render();
            deltaFPS++;
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

    public void addParticles(double xPos, double yPos, int ammount) {
        ArrayList<Particle> list = new ArrayList();
        for (int i = 0; i < ammount; i++) {
            list.add(new Particle(xPos, yPos, randomRange(0, 360),
                    (int) randomRange(15, 20), randomRange(1, 4)));
        }
        particles.addAll(list);
    }

    public static double randomRange(double min, double max) {
        double x = (double) ((Math.random() * ((max - min) + 1)) + min);
        return x;
    }

    public void addAsteroids(int ammount) {

        for (int i = 0; i < ammount; i++) {

            double x = randomRange(100 + ship.getXPos(), ship.getXPos() + dc.getWidth() - 100);
            double y = randomRange(100 + ship.getYPos(), ship.getXPos() + dc.getHeight() - 100);

            asteroids.add(new Asteroid(x, y, 3, texture));
        }

    }

    public void breakAsteroidWithPoints(int index) {

        int size = asteroids.get(index).getSize();

        if (size == 3) {
            score += 20;
        } else if (size == 2) {
            score += 50;
        } else if (size == 1) {
            score += 100;
        } else {

        }
        breakAsteroid(index);
    }

    public void breakAsteroid(int index) {

        breakAsteroid(asteroids.get(index));

    }

    public void breakAsteroid(Asteroid a) {
        addParticles(a.getXPos(),
                a.getYPos(), 6);

        int size = a.getSize();

        if (size > 1) {
            asteroids.add(new Asteroid(a, texture));
            asteroids.add(new Asteroid(a, texture));
        }

        asteroids.remove(a);
    }

    public boolean timerIsExpired(String name) {

        for (GameTimer t : gameTimers) {
            if (t.getName().equals(name)) {
                if (t.isExpired()) {
                    gameTimers.remove(t);
                    return true;
                } else {
                    return false;
                }

            }
        }
        return false;
    }

    public boolean timerExists(String name) {

        for (GameTimer t : gameTimers) {
            if (t.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean makeTimer(String name, long delay) {

        if (!timerExists(name)) {
            gameTimers.add(new GameTimer(name, delay, this.gameTick));
            return true;
        } else {
            return false;
        }
    }

    public boolean allShipCollision() {

        Asteroid a = collision.asteroidOfCollision(asteroids, ship.getPoints(dc), dc); //returns an asteroid that ship collided with if any
        if (a != null) {
            breakAsteroid(a);
            return true;
        }
        if (saucer != null) {
            if (collision.arePointsInHitZone(saucer.getHitZones(dc), ship.getPoints(dc), dc)) {  //detects if ship has hit suacer
                return true;
            }
        }
        Bullet b = collision.bulletInHitZones(ship.getHitZones(dc), bullets, dc); //detects if ship is hit by a bullet
        if (b != null) {
            bullets.remove(b);
            return true;
        }

        return false;
    }

    public boolean allSaucerCollision() {

        Asteroid a = collision.asteroidOfCollision(asteroids, saucer.getPoints(dc), dc); //returns an asteroid that ship collided with if any
        if (a != null) {
            breakAsteroid(a);
            return true;
        }
        Bullet b = collision.bulletInHitZones(saucer.getHitZones(dc), bullets, dc); //detects if ship is hit by a bullet
        if (b != null) {
            bullets.remove(b);
            return true;
        }
        return false;
    }

    public void allAsteroidCollision() {

        for (int a = asteroids.size() - 1; a >= 0; a--) {
            asteroids.get(a).update(dc);

            for (int b = bullets.size() - 1; b >= 0; b--) {

                if (collision.bulletInAsteroid(asteroids.get(a),
                        bullets.get(b), dc)) {

                    bullets.remove(b);
                    breakAsteroidWithPoints(a);
                    break;
                }
            }
        }
    }

    public void updateParticles() {

        for (int i = particles.size() - 1; i >= 0; i--) {
            if (particles.get(i).update(dc)) {
                particles.remove(i);
            }
        }
    }

    public void userInput() {
        if (dc.isKeyPressed('w')) {
            ship.moveForwards(0.15);
        }
        if (dc.isKeyPressed('a')) {
            ship.rotate(-4);
        }
        if (dc.isKeyPressed('d')) {
            ship.rotate(4);
        }
        if (dc.isKeyPressed('e')) {
            bullets.addAll(ship.sheild(360));
        }
        if (invince) {
            if (dc.isKeyPressed(' ')) {
                bullets.addAll(ship.superShoot(20));
            }
        } else {
            if (dc.getKeyPress(' ')) {
                bullets.add(ship.shoot());
            }
        }
        if (dc.getKeyPress(16)) {
            ship.hyperSpace(dc);
        }
        if (dc.getKeyPress('p')) {
            p = !p;
        }
        if (dc.getKeyPress('i')) {
            invince = !invince;
        }
        if (dc.getKeyPress('h')) {
            this.debug = !debug;
        }

        if (dc.getKeyPress('r')) {

            asteroids.clear();
            particles.clear();
            bullets.clear();
            level = 0;
            score = 0;
            lifes = 3;

            addAsteroids(4 + level);
        }
    }

    public void updateBullets() {

        for (int i = bullets.size() - 1; i >= 0; i--) {
            if (bullets.get(i).update(dc)) {
                bullets.remove(i);
            }
        }

    }

    public void drawLifes() {

        for (int i = 0; i < Math.abs(lifes); i++) {
           // dc.drawImage(texture.lifeLogo, 40 + (i * 40), 80);
        }
    }

    public void drawHitBoxes() {
        drawHitZones(ship.getHitZones(dc));
        drawHitPoints(ship.getPoints(dc));
        if (saucer != null) {
            drawHitZones(saucer.getHitZones(dc));
            drawHitPoints(saucer.getPoints(dc));
        }
        for (Asteroid a : asteroids) {
            drawHitZones(a.getHitZones(dc));
        }
    }

    public void drawHitZones(ArrayList<double[]> hitZones) {
        for (double[] hitZone : hitZones) {
            dc.drawEllipse(hitZone[1], hitZone[2], hitZone[0], hitZone[0]);
        }
    }

    public void drawHitPoints(ArrayList<double[][]> points) {
        for (double[][] point : points) {
            for (int i = 0; i < point[0].length; i++) {
                dc.fillRect(point[0][i], point[1][i], 5, 5);
            }
        }
    }

    public Color getRGBfromNumber(long value) {

        double temp = value - Math.floor(value / 360) * 360;
        double h = map(temp, 0, 360, 0, 1);
        double s = 1;
        double v = 1;

        return Color.getHSBColor((float) h, (float) s, (float) v);
    }

    final static double EPSILON = 1e-12;

    public double map(double valueCoord1,
            double startCoord1, double endCoord1,
            double startCoord2, double endCoord2) {

        if (Math.abs(endCoord1 - startCoord1) < EPSILON) {
            throw new ArithmeticException("/ 0");
        }

        double offset = startCoord2;
        double ratio = (endCoord2 - startCoord2) / (endCoord1 - startCoord1);
        return ratio * (valueCoord1 - startCoord1) + offset;
    }

}
