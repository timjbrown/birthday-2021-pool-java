import java.awt.Color;
import java.awt.Graphics;

public class Ball {

    private static double friction = .9;

    private double radius;
    private Color color;
    private Vector2d position; // pixels
    private Vector2d velocity; // pixels per second
    private Vector2d accel;
    private String name;
    private boolean moveable;
    private boolean collidable;
    private boolean alive;
    private double lifetime;
    private double lifespan;

    /**
     * Creates a Circle object with the given radius but random position,
     * velocity and color.
     * 
     * @param radius
     */
    public Ball(double radius, Color color, Vector2d position,
            Vector2d velocity, Vector2d accel, String name, boolean moveable,
            boolean collidable, double lifespan) {
        this.radius = radius;
        this.color = color;
        if (color == null)
            this.color = randomColor();
        this.position = position;
        if (position == null)
            this.position = randomPosition();
        this.velocity = velocity;
        if (velocity == null)
            this.velocity = randomVelocity();
        this.accel = accel;
        if (accel == null)
            this.accel = new Vector2d(0, 0);
        this.name = name;
        this.moveable = moveable;
        this.alive = true;
        this.lifespan = lifespan;
        this.collidable = collidable;
    }

    private Vector2d randomPosition() {
        return new Vector2d(radius, Game.PANEL_WIDTH - radius, radius,
                Game.PANEL_HEIGHT - radius);
    }

    private Vector2d randomVelocity() {
        return new Vector2d(-100, 100, -100, 100);
    }

    private Color randomColor() {
        return new Color((float) Math.random(), (float) Math.random(),
                (float) Math.random());
    }

    /**
     * Move the circle's location by it's velocity * elapsedTime
     */
    public void move(double elapsedTime) {
        velocity.add(accel.multed(elapsedTime));
        Vector2d frictionv = velocity.copy().mult(-1).mult(
                friction * elapsedTime);
        velocity.add(frictionv);
        position.add(velocity.multed(elapsedTime));

        if (velocity.magnitude() < .01) {
            velocity.x = 0;
            velocity.y = 0;
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
            if (position.x - radius < 0) {
                position.x = radius;
                velocity.x *= -1;
                bounced = true;
            }
            if (position.y - radius < 0) {
                position.y = radius;
                velocity.y *= -1;
                bounced = true;
            }
            if (position.x + radius > Game.PANEL_WIDTH) {
                position.x = Game.PANEL_WIDTH - radius;
                velocity.x *= -1;
                bounced = true;
            }
            if (position.y + radius > Game.PANEL_HEIGHT) {
                position.y = Game.PANEL_HEIGHT - radius;
                velocity.y *= -1;
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
        return this.position.distance(other.position);
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
        Tools.fillCenteredCircle(g, radius, position.x, position.y);
        g.setColor(Color.black);
        Tools.drawCenteredCircle(g, radius, position.x, position.y);
        if (name != null) {
            g.setColor(Color.white);
            Tools.drawCenterString(g, name, position.x, position.y);
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Vector2d getPosition() {
        return position;
    }

    public Vector2d getVelocity() {
        return velocity;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isCollidable() {
        return collidable;
    }
}