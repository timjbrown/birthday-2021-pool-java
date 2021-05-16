
public class Vector2d {

    public double x;
    public double y;

    /**
     * Creates a Vector2D object with the given x and y.
     * 
     * @param x
     * @param y
     */
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a Vector2D object with a random x and y in a range.
     * 
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     */
    public Vector2d(double minX, double maxX, double minY, double maxY) {
        this.x = Math.random() * (maxX - minX) + minX;
        this.y = Math.random() * (maxY - minY) + minY;
    }

    /**
     * Returns a copy of this vector.
     * 
     * @return a copy of this vector.
     */
    public Vector2d copy() {
        return new Vector2d(x, y);
    }

    /**
     * Returns the distance between this and other.
     * 
     * @param other
     * @return the distance between this and other.
     */
    public double distance(Vector2d other) {
        return Math.sqrt((other.x - this.x) * (other.x - this.x)
                + (other.y - this.y) * (other.y - this.y));
    }

    /**
     * Returns the magnitude of this vector.
     * 
     * @return the magnitude of this vector.
     */
    public double magnitude() {
        return Math.abs(distance(new Vector2d(0, 0)));
    }

    /**
     * Returns this vector normalized as a unit vector.
     * 
     * @return this vector normalized as a unit vector.
     */
    public Vector2d normalize() {
        double mag = magnitude();
        if (mag == 0)
            return this;
        x /= mag;
        y /= mag;
        return this;
    }

    /**
     * Returns a copy of this vector normalized as a unit vector.
     * 
     * @return a copy of this vector normalized as a unit vector.
     */
    public Vector2d normalized() {
        return copy().normalize();
    }

    /**
     * Returns this vector scaled by the given percent
     * 
     * @param percent
     * @return this vector scaled by the given percent
     */
    public Vector2d mult(double amt) {
        x *= amt;
        y *= amt;
        return this;
    }

    /**
     * Returns a copy of this vector scaled by the given percent
     * 
     * @param percent
     * @return a copy of this vector scaled by the given percent
     */
    public Vector2d multed(double amt) {
        return copy().mult(amt);
    }

    /**
     * Returns this vector + other vector
     * 
     * @param other
     * @return this vector + other vector
     */
    public Vector2d add(Vector2d other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    /**
     * Returns this vector + other vector
     * 
     * @param other
     * @return this vector + other vector
     */
    public Vector2d added(Vector2d other) {
        return copy().add(other);
    }

    /**
     * Returns this vector + other vector
     * 
     * @param other
     * @return this vector + other vector
     */
    public Vector2d sub(Vector2d other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    /**
     * Returns this vector + other vector
     * 
     * @param other
     * @return this vector + other vector
     */
    public Vector2d subbed(Vector2d other) {
        return copy().sub(other);
    }

    /**
     * Returns x as an int.
     * 
     * @return x as an int.
     */
    public int intX() {
        return (int) x;
    }

    /**
     * Returns y as an int.
     * 
     * @return y as an int.
     */
    public int intY() {
        return (int) y;
    }
}
