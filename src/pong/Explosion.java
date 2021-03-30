/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author landr
 */
public class Explosion {

    final int particleNum = 40;

    ArrayList<Particle> particles = new ArrayList<>();

    Explosion(Point p) {
        createParticles(p);
    }
    Explosion(Point p, Color color){
        createParticles(p, color);
    }

    void createParticles(Point p) {
        for (int i = 0; i < particleNum; i++) {
            particles.add(new Particle(p));
        }
    }
    void createParticles(Point p, Color color) {
        for (int i = 0; i < particleNum; i++) {
            particles.add(new Particle(p, color));
        }
    }

    public void moveParticles() {
        for (Particle particle : particles) {
            if (particle.moving()) {
                particle.move();
                particle.checkCollision();
            }
        }
    }

    public boolean particlesMoving() {
        boolean moving = false;
        for (Particle particle : particles) {
            if (particle.moving()) {
                moving = true;
            }
        }
        return moving;
    }

    public void draw(Graphics g) {
        for (Particle particle : particles) {
            g.setColor(particle.getColor());
            g.fillRect((int) particle.getX(), (int) particle.getY(), particle.getSize(), particle.getSize());
        }
    }

    public class Particle {

        final int SIZE = 20;
        final double MAX_SPEED = 30;
        final double DECELERATION = 0;

        Color COLOR;

        double speedX;
        double speedY;

        double x = 0;
        double y = 0;

        Random random;

        Particle(Point p) {
            random = new Random();
            randomSpeeds();
            randomColor();
            x = p.getX();
            y = p.getY();
        }
        
        Particle(Point p, Color color){
            random = new Random();
            COLOR = color;
            randomSpeeds();
            x = p.getX();
            y = p.getY();
        }

        void randomSpeeds() {
            speedX = (random.nextDouble() - 0.5) * MAX_SPEED;
            speedY = (random.nextDouble() - 0.5) * MAX_SPEED;
        }

        void randomColor() {
            COLOR = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }

        public void move() {
            x += speedX;
            y += speedY;
            checkSpeed();
            if (speedX != 0) {
                speedX -= (Math.abs(speedX) / speedX) * DECELERATION;
            }
            if (speedY != 0) {
                speedY -= (Math.abs(speedY) / speedY) * DECELERATION;
            }
        }

        void checkSpeed() {
            if (DECELERATION > Math.abs(speedX)) {
                speedX = 0;
            }
            if (DECELERATION > Math.abs(speedY)) {
                speedY = 0;
            }
        }

        public void checkCollision() {
            if (x <= 0) {
                speedX = Math.abs(speedX);
            } else if (x >= GamePanel.SCREEN_WIDTH - SIZE) {
                speedX = -Math.abs(speedX);
            }
            if (y <= 0) {
                speedY = Math.abs(speedY);
            } else if (y >= GamePanel.SCREEN_HEIGHT - SIZE) {
                speedY = -Math.abs(speedY);
            }
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public Color getColor() {
            return COLOR;
        }

        public int getSize() {
            return SIZE;
        }

        public boolean moving() {
            return ((speedX != 0) || (speedY != 0));
        }

    }
    
}
