package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import models.Pair;
import models.Vector2;
import tools.ColorTools;
import tools.DrawTools;
import tools.PhysTools;
import tools.SoundTools;

public class World {
    private String name;
    private ArrayList<Circle> balls;
    private ArrayList<Circle> pockets;
    public Circle cue;
    private boolean won = false;

    public World(String name) {
        this.name = name;
        balls = new ArrayList<Circle>();
        pockets = new ArrayList<Circle>();
        addBalls();
    }

    public void addBalls() {
        // Place balls
        int row = 0;
        int col = 0;
        for (int numBalls = 0; numBalls < name.length(); numBalls++) {
            Vector2 p = new Vector2(Settings.canvasRect.getWidth() / 2.0
                    - Settings.ballRadius * row + Settings.ballRadius * 2 * col,
                    200 - Settings.ballRadius * 2 * row);
            Circle c = new Circle();
            c.setName(name.charAt(numBalls) + "");
            c.setRadius(Settings.ballRadius);
            c.setColor(ColorTools.randomColor());
            c.setPos(p);
            c.setFriction(Settings.friction);

            balls.add(c);
            col++;
            if (col > row) {
                col = 0;
                row++;
            }
        }

        // Place pockets
        for (int i = 0; i < 6; i++) {
            Vector2 p = new Vector2(Settings.canvasRect.getWidth() * (i % 2),
                    Settings.canvasRect.getHeight() / 2.0 * (i / 2));
            Circle c = new Circle();
            c.setRadius(Settings.ballRadius);
            c.setColor(Color.black);
            c.setPos(p);
            c.setMovable(false);
            balls.add(c);
            pockets.add(c);
        }

        // Place player
        Vector2 p = Settings.cueStartPos.copy();
        cue = new Circle();
        cue.setRadius(Settings.ballRadius);
        cue.setColor(Color.white);
        cue.setPos(p);
        cue.setFriction(Settings.friction);
        balls.add(cue);
    }

    public void step(double elapsedTime) {
        int subframes = 2;
        elapsedTime = elapsedTime / subframes;

        for (int f = 0; f < subframes; f++) {
            update(elapsedTime);
            ArrayList<Pair<Circle, Circle>> pairs = detectCollisions();
            resolveCollisions(pairs);
            bounceOffWalls();
            for (int i = 0; i < balls.size(); i++) {
                if (!balls.get(i).isAlive()) {
                    balls.remove(i);
                    i--;
                }
            }
            if (!cue.isAlive()) {
                cue.setAlive(true);
                cue.setPos(Settings.cueStartPos.copy());
                cue.setVel(Vector2.zero());
                balls.add(cue);
            }
            if (balls.size() < 8) {
                won = true;
            }
            if (won) {
                winAnimation();
            }
        }
    }

    private void winAnimation() {
        double speed = 200;
        Vector2 p = new Vector2(Settings.canvasRect.getWidth() / 2,
                Settings.canvasRect.getHeight() * .48);
        Vector2 v = new Vector2(-speed * .7, speed * .7, -speed * 1.5,
                -speed * .5);
        Vector2 a = new Vector2(0, 300);
        Circle c = new Circle();
        c.setRadius(2);
        c.setColor(ColorTools.randomColor());
        c.setPos(p);
        c.setVel(v);
        c.setAcc(a);
        c.setMovable(true);
        c.setCollidable(false);
        c.setDisplaceable(false);
        c.setLifespan(1.2);
        balls.add(c);
    }

    public void draw(Graphics g, int width, int height) {
        g.setColor(Settings.bgColor);
        g.fillRect(0, 0, width, height);
        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).draw(g);
        }
        if (won) {
            g.setColor(Color.white);
            DrawTools.drawString(g, "HAPPY BIRTHDAY " + name,
                    Settings.canvasRect.getWidth() / 2.0,
                    Settings.canvasRect.getHeight() * .23);
        }
    }

    private void update(double elapsedTime) {
        for (Circle c : balls) {
            c.update(elapsedTime, Settings.forces);
        }
    }

    private ArrayList<Pair<Circle, Circle>> detectCollisions() {
        ArrayList<Pair<Circle, Circle>> pairs = new ArrayList<Pair<Circle, Circle>>();
        for (int i = 0; i < balls.size(); i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                Circle c1 = balls.get(i);
                Circle c2 = balls.get(j);
                if (c1 != c2 && c1.isCollidable() && c2.isCollidable()
                        && PhysTools.colliding(c1, c2)) {
                    if (c1.isMovable() && !c2.isMovable()) {
                        c1.setAlive(false);
                        SoundTools.playSound("sink.wav");
                    } else if (c2.isMovable() && !c1.isMovable()) {
                        c2.setAlive(false);
                        SoundTools.playSound("sink.wav");
                    } else {
                        Pair<Circle, Circle> p = new Pair<Circle, Circle>(c1,
                                c2);
                        pairs.add(p);
                    }
                }
            }
        }
        return pairs;
    }

    private void resolveCollisions(ArrayList<Pair<Circle, Circle>> pairs) {
        for (Pair<Circle, Circle> pair : pairs) {
            SoundTools.playSound("hit.wav");
            PhysTools.collide(pair.o1, pair.o2, Settings.ballRestitution);
        }
    }

    private void bounceOffWalls() {
        for (Circle c : balls) {
            if (PhysTools.bounceOffWalls(Settings.canvasRect, c,
                    Settings.wallRestitution)) {
                SoundTools.playSound("bounce.wav");
            }
        }
    }
}