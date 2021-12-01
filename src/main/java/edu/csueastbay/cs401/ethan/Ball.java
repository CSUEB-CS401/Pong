package edu.csueastbay.cs401.ethan;

import edu.csueastbay.cs401.ethan.game.Collidable;
import edu.csueastbay.cs401.ethan.game.Collision;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Ball extends NeonEntity implements Collidable {

    ObjectProperty<Paddle> owner;
    private final Circle collision;
    DoubleProperty radius;
    double dx, dy;
    double mass;

    public Ball(double radius, double x, double y) {
        this.radius = new SimpleDoubleProperty(radius);
        this.x = x;
        this.y = y;
        dx = 0;
        dy = 0;
        mass = 1;

        owner = new SimpleObjectProperty<>();
        owner.addListener((obs, oldVal, newVal)->{
            fillColor.set(newVal.fillColor.get());
            glowColor.set(newVal.glowColor.get());
        });


        solid.set(true);
        Circle visual = new Circle(radius);
        visual.radiusProperty().bind(this.radius);
        this.bindStyle(visual);
        addNode(visual);

        this.collision = new Circle(radius);
        collision.radiusProperty().bind(this.radius);
        collision.setVisible(false);
        addNode(collision);
    }

    public Ball(double radius, double x, double y, double speed, double direction) {
        this(radius, x, y);
        dx = Math.cos(direction)*speed;
        dy = Math.sin(direction)*speed;
    }

    public Ball(Ball other) {
        this(other.radius.get(), other.x, other.y);
        this.dx = other.dx;
        this.dy = other.dy;
        this.owner.set(other.owner.get());
        this.exceptions.add(other);
        other.exceptions.add(this);
    }

    private Set<Collidable> exceptions = new HashSet<>();
    @Override
    public void update(double delta) {
        Set<Collidable> collisions = new HashSet<>();

        for(Collision collision : game.getCollisions(this)) {
            collisions.add(collision.other());
            if(exceptions.contains(collision.other())) continue;
            if(collision.other() instanceof Ball other) {
                other.exceptions.add(this);
                handleCollision(other);
            } else if(collision.other() instanceof Paddle paddle) {
                Point2D point = new Point2D(
                        collision.intersection().getBoundsInLocal().getCenterX(),
                        collision.intersection().getBoundsInLocal().getCenterY());
                Point2D norm = new Point2D(x, y).subtract(point).normalize();
                Point2D vel = new Point2D(dx, dy);
                vel = vel.subtract(0, paddle.getYSpeed());
                vel = vel.subtract(norm.multiply(2*vel.dotProduct(norm)));
                vel = vel.add(0, paddle.getYSpeed());
                dx = vel.getX();
                dy = vel.getY();
                owner.set(paddle);
                pulse();
                paddle.pulse();
            }
        }
        exceptions = collisions;

        x += dx * delta;
        y += dy * delta;
        double temp;
        temp = game.bounds.getMinY()+radius.get();
        if(y < temp){
            y = temp-(y-temp);
            dy *= -1;
        }
        temp = game.bounds.getMaxY()-radius.get();
        if(y > temp){
            y = temp-(y-temp);
            dy *= -1;
        }
    }

    @Override
    public Shape getCollisionShape() {
        return collision;
    }

    public void launch() {
        Random random = new Random();
        double speed = 250;
        double angle = random.nextDouble()*90-45;
        if(random.nextBoolean()) angle += 180;
        angle = Math.toRadians(angle);
        dx = Math.cos(angle)*speed;
        dy = Math.sin(angle)*speed;
    }

    // Code here adapted from https://ericleong.me/research/circle-circle/
    private void handleCollision(Ball other) {
        double relativeDX = dx-other.dx;
        double relativeDY = dy - other.dy;
        Point2D d = closestPointOnLine(x-relativeDX, y-relativeDY,
                x, y, other.x, other.y);
        double closestdistsq = Math.pow(other.x - d.getX(), 2) + Math.pow(other.y - d.getY(), 2);
        double backdist = Math.sqrt(Math.pow(radius.get() + other.radius.get(), 2) - closestdistsq);
        double movementvectorlength = Math.sqrt(Math.pow(relativeDX, 2) + Math.pow(relativeDY, 2));
        double c_x = d.getX() - backdist * (relativeDX / movementvectorlength);
        double c_y = d.getY() - backdist * (relativeDY / movementvectorlength);

        Point2D norm = new Point2D(c_x-other.x, c_y-other.y).normalize();
        double p = 2 * (new Point2D(dx, dy).dotProduct(norm) - new Point2D(other.dx, other.dy).dotProduct(norm)) /
                (mass + other.mass);
        dx = dx - p * mass * norm.getX();
        dy = dy - p * mass * norm.getY();
        other.dx = other.dx + p * other.mass * norm.getX();
        other.dy = other.dy + p * other.mass * norm.getY();
    }

    // Code here adapted from https://ericleong.me/research/circle-circle/
    private Point2D closestPointOnLine(double lineX1, double lineY1, double lineX2, double lineY2, double pointX, double pointY){
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
