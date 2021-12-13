package edu.csueastbay.cs401.psander.engine.math;

/**
 * A basic 2D vector class
 */
public class Vector2D {
    private double _x = 0.0;
    private double _y = 0.0;

    // Constructors

    /**
     * Constructs a new Vector2D object with default
     * values (0, 0).
     */
    public Vector2D() { }

    /**
     * Constructs a new Vector2D object
     * with the specified values.
     * @param x The x parameter of the object.
     * @param y The y parameter of the object.
     */
    public Vector2D(double x, double y) {
        _x = x;
        _y = y;
    }

    /**
     * Copy constructor.
     * @param v1 The vector to copy.
     */
    public Vector2D(Vector2D v1) {
        _x = v1._x;
        _y = v1._y;
    }

    // Static methods

    /**
     * Adds the second vector to the first and returns
     * the result as a new vector.
     * @param v1 The first vector to add.
     * @param v2 The second vector to add.
     * @return The vector sum of both vectors.
     */
    public static Vector2D add(Vector2D v1, Vector2D v2) {
        return new Vector2D( v1._x + v2._x, v1._y + v2._y);
    }

    /**
     * Subtracts the second vector from the first and returns
     * the result as a new vector.
     * @param v1 The first vector to add.
     * @param v2 The second vector to add.
     * @return The vector difference of both vectors.
     */
    public static Vector2D subtract(Vector2D v1, Vector2D v2) {
        return new Vector2D( v1._x - v2._x, v1._y - v2._y);
    }

    /**
     * Performs scalar multiplication of a vector and the given factor.
     * @param v1 The vector to scale.
     * @param factor The scalar to scale by.
     * @return A new, re-scaled vector.
     */
    public static Vector2D scale(Vector2D v1, double factor) {
        return new Vector2D( v1._x * factor, v1._y * factor);
    }

    // Instance methods


    /**
     * Retrieves the x-component of the vector.
     * @return The vector's x-component.
     */
    public double X() { return _x; }

    /**
     * Sets the vector's x-component to the specified value.
     * @param x The new x-component for the vector.
     */
    public void setX(double x) { _x = x; }

    /**
     * Retrieves the y-component of the vector.
     * @return The vector's y-component.
     */
    public double Y() { return _y; }

    /**
     * Sets the vector's y-component to the specified value.
     * @param y The new y-component for the vector.
     */
    public void setY(double y) { _y = y; }


    /**
     * Sets the values of the current vector to match
     * that of the specified one.
     * @param v1 The vector whose values are to be
     *           copied.
     */
    public void set(Vector2D v1) { set(v1._x, v1._y); }

    /**
     * Sets the vector's x and y components to the
     * specified ones.
     * @param x The vector's new x-component.
     * @param y The vector's new y-component.
     */
    public void set(double x, double y) {
        _x = x;
        _y = y;
    }

    /**
     * Adds the specified vector to this one.
     * @param v1 The vector to add.
     */
    public void add(Vector2D v1) {
        _x += v1._x;
        _y += v1._y;
    }


    /**
     * Subtracts the specified vector from this one.
     * @param v1 The vector to subtract.
     */
    public void subtract(Vector2D v1) {
        _x -= v1._x;
        _y -= v1._y;
    }

    /**
     * Gets the length of the vector.
     * @return The vector's length as a scalar.
     */
    public double length() { return Math.hypot(_x, _y); }

    /**
     * Performs scalar multiplication, modifying
     * the length of the vector without changing
     * its direction.
     * @param factor The scalar to scale the length by.
     */
    public void scale(double factor) { scale(factor, factor); }

    /**
     * Scales each component of the vector by a specified
     * amount, without guaranteeing the direction remains
     * unchanged.
     * @param scaleX The amount to scale the x-component by.
     * @param scaleY The amount to scale the y-component by.
     */
    public void scale(double scaleX, double scaleY) {
        _x *= scaleX;
        _y *= scaleY;
    }

    /**
     * Normalizes the vector, keeping the direction
     * the same but changing the magnitude to be 1.
     * A zero vector is left unchanged by this
     * operation.
     */
    public void normalize() {
        var len = this.length();
        if (len == 0.0) return;

        scale(1 / len);
    }

    @Override
    public String toString() {
        return "(" + _x + ", " + _y + ")";
    }
}
