import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class World {
    private ArrayList<Ball> balls;
    private ArrayList<Ball> pockets;
    public Ball cue;
    private double playerStartX = Game.PANEL_WIDTH / 2.0;
    private double playerStartY = Game.PANEL_HEIGHT * .75;
    private int ballRadius = 10;
    private Color bgColor = new Color(6, 145, 84);
    private String name;
    private boolean won = false;

    /**
     * Adds 10 circle objects to circles.
     */
    public World() {
        balls = new ArrayList<Ball>();
        pockets = new ArrayList<Ball>();
        name = JOptionPane.showInputDialog("What is your name?").toUpperCase();
        addBalls();
    }

    public void addBalls() {
        // Place balls
        int row = 0;
        int col = 0;
        for (int numBalls = 0; numBalls < name.length(); numBalls++) {
            Vector2 p = new Vector2(Game.PANEL_WIDTH / 2.0 - ballRadius * row
                    + ballRadius * 2 * col, 200 - ballRadius * 2 * row);
            Ball b = new Ball(ballRadius, null, p, new Vector2(0, 0), null,
                    name.charAt(numBalls) + "", true, true, -1);
            balls.add(b);
            col++;
            if (col > row) {
                col = 0;
                row++;
            }
        }

        // Place pockets
        for (int i = 0; i < 6; i++) {
            Vector2 p = new Vector2(Game.PANEL_WIDTH * (i % 2),
                    Game.PANEL_HEIGHT / 2.0 * (i / 2));
            Ball pocket = new Ball(ballRadius, Color.black, p,
                    new Vector2(0, 0), null, null, false, true, -1);
            balls.add(pocket);
            pockets.add(pocket);
        }

        // Place player
        Vector2 p = new Vector2(playerStartX, playerStartY);
        cue = new Ball(ballRadius, Color.white, p, new Vector2(0, 0), null,
                null, true, true, -1);
        balls.add(cue);
    }

    /**
     * Steps the simulation once.
     * 
     * @param elapsedTime
     *            the elapsed time between steps in seconds
     */
    public void step(double elapsedTime) {
        int subframes = 2;
        elapsedTime = elapsedTime / subframes;

        for (int f = 0; f < subframes; f++) {
            move(elapsedTime);
            ArrayList<Pair<Ball, Ball>> pairs = detectCollisions();
            resolveCollisions(pairs);
            bounceOffWalls();
            for (int i = 0; i < balls.size(); i++) {
                if (!balls.get(i).alive) {
                    balls.remove(i);
                    i--;
                }
            }
            if (!cue.alive) {
                cue.alive = true;
                cue.pos.x = playerStartX;
                cue.pos.y = playerStartY;
                cue.vel.x = 0;
                cue.vel.y = 0;
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
        Vector2 p = new Vector2(Game.PANEL_WIDTH / 2, Game.PANEL_HEIGHT * .48);
        Vector2 v = new Vector2(-speed * .8, speed * .8, -speed * 1.5,
                -speed * .5);
        Vector2 a = new Vector2(0, 200);
        Ball b = new Ball(2, null, p, v, a, null, true, false, 2);
        balls.add(b);
    }

    /**
     * Call drawCenteredCircle() on every circle in circles
     * 
     * @param g
     * @param width
     * @param height
     */
    public void draw(Graphics g, int width, int height) {
        g.setColor(bgColor);
        g.fillRect(0, 0, width, height);
        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).draw(g);
        }
        if (won) {
            g.setColor(Color.white);
            Tools.drawCenterString(g, "HAPPY BIRTHDAY " + name,
                    Game.PANEL_WIDTH / 2.0, Game.PANEL_HEIGHT * .23);
        }
    }

    /**
     * Move all circles by their current velocity.
     */
    private void move(double elapsedTime) {
        for (Ball c : balls) {
            c.move(elapsedTime);
        }
    }

    /**
     * Detect collisions between circles.
     * 
     * @return a list of colliding circle pairs
     */
    private ArrayList<Pair<Ball, Ball>> detectCollisions() {
        ArrayList<Pair<Ball, Ball>> pairs = new ArrayList<Pair<Ball, Ball>>();
        for (int i = 0; i < balls.size(); i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                Ball c1 = balls.get(i);
                Ball c2 = balls.get(j);
                Pair<Ball, Ball> p = new Pair<Ball, Ball>(c1, c2);
                if (c1 != c2 && c1.collidable && c2.collidable
                        && c1.isColliding(c2)) {
                    if (c1.moveable && !c2.moveable) {
                        c1.alive = false;
                        Tools.playSound("sink.wav");
                    } else if (c2.moveable && !c1.moveable) {
                        c2.alive = false;
                        Tools.playSound("sink.wav");
                    } else
                        pairs.add(p);
                }
            }
        }
        return pairs;
    }

    /**
     * Resolves collisions between circles.
     */
    private void resolveCollisions(ArrayList<Pair<Ball, Ball>> pairs) {
        for (Pair<Ball, Ball> pair : pairs) {
            Tools.playSound("hit.wav");
            Ball c1 = pair.c1;
            Ball c2 = pair.c2;
            changePositions(c1, c2);
        }

        for (Pair<Ball, Ball> pair : pairs) {
            Ball c1 = pair.c1;
            Ball c2 = pair.c2;
            changeVelocities(c1, c2);
        }
    }

    /**
     * Changes positions of colliding circles to prevent overlap
     * 
     * @param c1
     * @param c2
     */
    private void changePositions(Ball c1, Ball c2) {
        double distance = c1.distance(c2);
        double overlap = .5 * (distance - c1.radius - c2.radius);

        if (c1.moveable) {
            if (!c2.moveable)
                overlap *= 2;
            c1.pos.x -= overlap * (c1.pos.x - c2.pos.x) / distance;
            c1.pos.y -= overlap * (c1.pos.y - c2.pos.y) / distance;
        }
        if (c2.moveable) {
            if (!c1.moveable)
                overlap *= 2;
            c2.pos.x += overlap * (c1.pos.x - c2.pos.x) / distance;
            c2.pos.y += overlap * (c1.pos.y - c2.pos.y) / distance;
        }
    }

    /**
     * Changes velocity of colliding circles to bounce properly
     * 
     * @param c1
     * @param c2
     */
    private void changeVelocities(Ball c1, Ball c2) {
        double mass = 1;
        double distance = c1.distance(c2);
        double nx = (c2.pos.x - c1.pos.x) / distance;
        double ny = (c2.pos.y - c1.pos.y) / distance;
        double tx = -ny;
        double ty = nx;
        double kx = c1.vel.x - c2.vel.x;
        double ky = c1.vel.y - c2.vel.y;
        double p = 2.0 * (nx * kx + ny * ky) / (mass + mass);
        if (c1.moveable) {
            c1.vel.x -= p * mass * nx;
            c1.vel.y -= p * mass * ny;
        }
        if (c2.moveable) {
            c2.vel.x += p * mass * nx;
            c2.vel.y += p * mass * ny;
        }
    }

    /**
     * Bounce circles off walls if necessary
     */
    private void bounceOffWalls() {
        for (Ball c : balls) {
            if (c.bounceOffWall()) {
                Tools.playSound("bounce.wav");
            }
        }
    }
}