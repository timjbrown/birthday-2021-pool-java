import java.awt.Color;
import java.awt.Graphics;

public class Ball {

    static double friction = .9;

    double radius;
    Color color;
    Vector2 pos; // pixels
    Vector2 vel; // pixels/second
    Vector2 acc; // pixels/second/second
    String name;
    boolean moveable;
    boolean collidable;
    boolean alive;
    double lifetime;
    double lifespan;

    /**
     * Creates a Circle object with the given radius but random position,
     * velocity and color.
     * 
     * @param radius
     */
    public Ball(double radius, Color color, Vector2 position, Vector2 velocity,
            Vector2 accel, String name, boolean moveable, boolean collidable,
            double lifespan) {
        this.radius = radius;
        this.color = color;
        if (color == null)
            this.color = randomColor();
        this.pos = position;
        if (position == null)
            this.pos = randomPosition();
        this.vel = velocity;
        if (velocity == null)
            this.vel = randomVelocity();
        this.acc = accel;
        if (accel == null)
            this.acc = new Vector2(0, 0);
        this.name = name;
        this.moveable = moveable;
        this.alive = true;
        this.lifespan = lifespan;
        this.collidable = collidable;
    }

    private Vector2 randomPosition() {
        return new Vector2(radius, Game.PANEL_WIDTH - radius, radius,
                Game.PANEL_HEIGHT - radius);
    }

    private Vector2 randomVelocity() {
        return new Vector2(-100, 100, -100, 100);
    }

    private Color randomColor() {
        return new Color((float) Math.random(), (float) Math.random(),
                (float) Math.random());
    }

    /**
     * Move the circle's location by it's velocity * elapsedTime
     */
    public void move(double elapsedTime) {
        Vector2 frictionv = vel.copy().mul(-1 * friction * elapsedTime);

        vel.add(acc.copy().mul(elapsedTime));
        vel.add(frictionv);
        pos.add(vel.copy().mul(elapsedTime));

        if (vel.length() < .01) {
            vel.zero();
        }

        lifetime += elapsedTime;
        if (lifespan > -1 && lifetime >= lifespan)
            alive = false;
    }

    /**
     * Bounces the circle off the wall if necessary
     */
    public boolean bounceOffWall() {
        boolean bounced = false;
        if (collidable) {
            if (pos.x - radius < 0) {
                pos.x = radius;
                vel.x *= -1;
                bounced = true;
            }
            if (pos.y - radius < 0) {
                pos.y = radius;
                vel.y *= -1;
                bounced = true;
            }
            if (pos.x + radius > Game.PANEL_WIDTH) {
                pos.x = Game.PANEL_WIDTH - radius;
                vel.x *= -1;
                bounced = true;
            }
            if (pos.y + radius > Game.PANEL_HEIGHT) {
                pos.y = Game.PANEL_HEIGHT - radius;
                vel.y *= -1;
                bounced = true;
            }
        }
        return bounced;
    }

    /**
     * Returns the distance between this and other.
     * 
     * @param other
     * @return the distance between this and other.
     */
    public double distance(Ball other) {
        return this.pos.dist(other.pos);
    }

    /**
     * Returns true if this is colliding with other, false otherwise.
     * 
     * @param other
     * @return true if this is colliding with other, false otherwise.
     */
    public boolean isColliding(Ball other) {
        return this.distance(other) < this.radius + other.radius;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        Tools.fillCenteredCircle(g, radius, pos.x, pos.y);
        g.setColor(Color.black);
        Tools.drawCenteredCircle(g, radius, pos.x, pos.y);
        if (name != null) {
            g.setColor(Color.white);
            Tools.drawCenterString(g, name, pos.x, pos.y);
        }
    }
}