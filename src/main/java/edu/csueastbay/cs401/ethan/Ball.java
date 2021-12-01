package edu.csueastbay.cs401.ethan;

import edu.csueastbay.cs401.ethan.game.Collidable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Ball is a subclass of Entity which bounces around the screen and is the primary focus of many aspects of Pong
 */
public class Ball extends NeonEntity implements Collidable {

    /** The last {@link Paddle} this Ball collided with. Controls the color of the ball by default.*/
    ObjectProperty<Paddle> owner;
    /** This ball's {@link Ball#getCollisionShape() collision shape} */
    private final Circle collision;
    DoubleProperty radius;
    /** The velocity of this ball. */
    double dx, dy;
    /** The mass of this ball. Used for collisions between two balls. */
    double mass;

    /**
     * Constructs a Ball with the given radius, at the given position
     * @param radius the size of the ball
     * @param x the horizontal position of the ball
     * @param y the vertical position of the ball
     */
    public Ball(double radius, double x, double y) {
        this.radius = new SimpleDoubleProperty(radius);
        this.x = x;
        this.y = y;
        dx = 0;
        dy = 0;
        mass = 1;

        owner = new SimpleObjectProperty<>();
        owner.addListener((obs, oldVal, newVal)->{
            // when the owner changes, change color to match
            fillColor.set(newVal.fillColor.get());
            glowColor.set(newVal.glowColor.get());
        });

        // Set up visible neon circle
        solid.set(true);
        Circle visual = new Circle(radius);
        visual.radiusProperty().bind(this.radius);
        this.bindStyle(visual);
        addNode(visual);

        // Set up invisible collision shape
        this.collision = new Circle(radius);
        collision.radiusProperty().bind(this.radius);
        collision.setVisible(false);
        addNode(collision);
    }

    /** Constructs a Ball which copies the attributes of the given Ball. */
    @SuppressWarnings("CopyConstructorMissesField") // collision is a duplicate circle, but not a copied reference
    public Ball(Ball other) {
        this(other.radius.get(), other.x, other.y);
        this.dx = other.dx;
        this.dy = other.dy;
        this.mass = other.mass;
        this.owner.set(other.owner.get());

        // also ensure the new ball doesn't immediately "bounce off" the old one, since they will certainly collide
        this.exceptions.add(other);
        other.exceptions.add(this);
    }

    // Maintain a list of exceptions to prevent repeated collisions in case of incomplete separation after bouncing
    private Set<Collidable> exceptions = new HashSet<>();

    /**
     * Balls move based on their speed ({@link Ball#dx}, {@link Ball#dy}), and bounce off of other Balls,
     *  {@link Paddle Paddles}, and the top and bottom of {@link Ball#game}'s {@link PongGame#bounds bounds}.
     * @param delta elapsed time since last call, in seconds. Used to normalize motion.
     */
    @Override
    public void update(double delta) {
        Set<Collidable> newExceptions = new HashSet<>();  // to update exceptions
        for(var collision : game.getCollisions(this)) {
            newExceptions.add(collision.other());   // don't collide with the same thing twice in a row
            if(exceptions.contains(collision.other())) continue;  // this is the definition of an exception

            if(collision.other() instanceof Ball other) {
                // other Balls get their own fancy function:
                handleCollision(other);
            } else if(collision.other() instanceof Paddle paddle) {
                // Paddles we treat as moving immovable objects and bounce off the approximate normal.

                // Assume the collision point is the center of the intersection
                Point2D point = new Point2D(
                        collision.intersection().getBoundsInLocal().getCenterX(),
                        collision.intersection().getBoundsInLocal().getCenterY());

                Point2D norm = new Point2D(x, y).subtract(point).normalize();  // all circle collisions are tangent
                Point2D vel = new Point2D(dx, dy);                             // store velocity for vector ops
                vel = vel.subtract(0, paddle.getYSpeed());                  // Adjust frame of reference to paddle
                vel = vel.subtract(norm.multiply(2*vel.dotProduct(norm)));     // Bounce velocity off of normal surface
                vel = vel.add(0, paddle.getYSpeed());                       // Revert frame of reference

                // decompose velocity
                dx = vel.getX();
                dy = vel.getY();

                // set possession owner (and thus color)
                owner.set(paddle);

                // fancy blink!
                pulse();
                paddle.pulse();
            }
        }
        exceptions = newExceptions;

        // move by velocity
        x += dx * delta;
        y += dy * delta;

        // bounds collisions are easy, so don't use collisions
        double minY = game.bounds.getMinY()+radius.get();
        if(y < minY){           // if we're above minY
            y = minY+(minY-y);  // reflect y about minY
            dy *= -1;           // bounce
        }
        double maxY = game.bounds.getMaxY()-radius.get();
        if(y > maxY){           // if we're below maxY
            y = maxY-(y-maxY);  // reflect y about maxY
            dy *= -1;           // bounce
        }
    }

    @Override
    public Shape getCollisionShape() {
        return collision;
    }

    /** Fires the ball in a random direction */
    public void launch() {
        Random random = new Random();
        double speed = 250;
        double angle = random.nextDouble()*90-45;
        if(random.nextBoolean()) angle += 180;
        angle = Math.toRadians(angle);
        dx = Math.cos(angle)*speed;
        dy = Math.sin(angle)*speed;
    }

    /**
     * Calculates collision reaction for this and the given ball, based on calculating the angle of collision to
     * semi-accurately bounce billiard-style.
     * @param other the ball to collide with
     */
    // Code here adapted with heavy modification from https://ericleong.me/research/circle-circle/
    private void handleCollision(Ball other) {
        // This ball will calculate the results for other, as well, to save processing
        other.exceptions.add(this);

        // calculate relative speed to simplify calculations
        double relativeDX = dx-other.dx;
        double relativeDY = dy - other.dy;

        // Get the closest point to the other ball on the line segment this ball travelled
        Point2D d = closestPointOnLine(x-relativeDX, y-relativeDY, x, y, other.x, other.y);

        // Do some mathemagics on it to get the point of the collision
        double closestdistsq = Math.pow(other.x - d.getX(), 2) + Math.pow(other.y - d.getY(), 2);
        double backdist = Math.sqrt(Math.pow(radius.get() + other.radius.get(), 2) - closestdistsq);
        double movementvectorlength = Math.sqrt(Math.pow(relativeDX, 2) + Math.pow(relativeDY, 2));
        double c_x = d.getX() - backdist * (relativeDX / movementvectorlength);
        double c_y = d.getY() - backdist * (relativeDY / movementvectorlength);

        // the normal is the same as the vector from other to the collision, since circle collisions are always on tangents
        Point2D norm = new Point2D(c_x-other.x, c_y-other.y).normalize();

        // we use the normal and the masses to calculate output velocities (based on conservation of momentum and speed in elastic collisions)
        double p = 2 * (new Point2D(dx, dy).dotProduct(norm) - new Point2D(other.dx, other.dy).dotProduct(norm)) / (mass + other.mass);
        dx = dx - p * mass * norm.getX();
        dy = dy - p * mass * norm.getY();
        other.dx = other.dx + p * other.mass * norm.getX();
        other.dy = other.dy + p * other.mass * norm.getY();
    }

    /** Algebraically calculates the closest point on a Line segment to a point */
    // Code here adapted with light modification from https://ericleong.me/research/circle-circle/
    private Point2D closestPointOnLine(double lineX1, double lineY1, double lineX2, double lineY2, double pointX, double pointY) {
        double A1 = lineY2 - lineY1;
        double B1 = lineX1 - lineX2;
        double C1 = (lineY2 - lineY1)*lineX1 + (lineX1 - lineX2)*lineY1;
        double C2 = -B1*pointX + A1*pointY;
        double det = A1*A1 + B1*B1;
        double cx = 0;
        double cy = 0;
        if(det != 0) {
            cx = (A1*C1 - B1*C2)/det;
            cy = (A1*C2 + B1*C1)/det;
        } else {
            cx = pointX;
            cy = pointY;
        }
        return new Point2D(cx, cy);
    }
}
