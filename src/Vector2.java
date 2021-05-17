
public class Vector2 {

    public double x;
    public double y;

    /**
     * Creates a Vector2D object with the given x and y.
     * 
     * @param x
     * @param y
     */
    public Vector2(double x, double y) {
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
    public Vector2(double minX, double maxX, double minY, double maxY) {
        this.x = Math.random() * (maxX - minX) + minX;
        this.y = Math.random() * (maxY - minY) + minY;
    }

    /**
     * Returns a copy of this vector.
     * 
     * @return a copy of this vector.
     */
    public Vector2 copy() {
        return new Vector2(x, y);
    }

    public void zero() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Returns the distance between this and other.
     * 
     * @param other
     * @return the distance between this and other.
     */
    public double dist(Vector2 other) {
        return Math.sqrt((other.x - this.x) * (other.x - this.x)
                + (other.y - this.y) * (other.y - this.y));
    }

    /**
     * Returns the magnitude of this vector.
     * 
     * @return the magnitude of this vector.
     */
    public double length() {
        return Math.abs(dist(new Vector2(0, 0)));
    }

    /**
     * Returns this vector normalized as a unit vector.
     * 
     * @return this vector normalized as a unit vector.
     */
    public Vector2 normal() {
        double length = length();
        if (length == 0)
            return this;
        x /= length;
        y /= length;
        return this;
    }

    /**
     * Returns this vector scaled by the given percent
     * 
     * @param percent
     * @return this vector scaled by the given percent
     */
    public Vector2 mul(double amt) {
        x *= amt;
        y *= amt;
        return this;
    }

    /**
     * Returns this vector + other vector
     * 
     * @param other
     * @return this vector + other vector
     */
    public Vector2 add(Vector2 other) {
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
    public Vector2 sub(Vector2 other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
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
