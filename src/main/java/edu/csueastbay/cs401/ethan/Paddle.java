package edu.csueastbay.cs401.ethan;

import edu.csueastbay.cs401.ethan.game.Collidable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Paddles are the player-controlled Entities in Pong.
 */
public class Paddle extends NeonEntity implements Collidable {

    /** This paddle's {@link Paddle#getCollisionShape() collision shape} */
    private final Path collision;
    /** A control this paddle checks to move */
    private final String up, down;

    /** The vertical span of this Paddle */
    public final DoubleProperty height;
    /** The rate this paddle moves */
    public double speed;

    /**
     * Creates a Paddle which uses the given inputs to move, and has the given color.
     * @param upInput the control for this paddle to move up
     * @param downInput the control for this paddle to move down
     * @param color the color of this paddle
     * @see PongGame#input
     */
    public Paddle(String upInput, String downInput, Color color) {
        this.up = upInput;
        this.down = downInput;
        this.height = new SimpleDoubleProperty(75);
        this.speed = 250;

        // fillColor should be brighter, to look more neon
        this.fillColor.set(color.interpolate(Color.WHITE, 0.5));
        this.glowColor.set(color);

        Path visual = generateShape();
        this.collision = new Path(visual.getElements());
        collision.setVisible(false);
        this.bindStyle(visual);
        addNode(visual);
        addNode(collision);
    }

    /**
     * Creates a semi-rounded rectangle shape, with dimensions bound to {@link Paddle#height}
     * @return the generated shape
     */
    private Path generateShape() {
        Path path = new Path();
        NumberBinding halfHeight, negHalfHeight;
        halfHeight = Bindings.divide(height, 2);
        negHalfHeight = Bindings.divide(height, -2);

        MoveTo start = new MoveTo(-5, 0);
        start.yProperty().bind(negHalfHeight);
        path.getElements().add(start);

        LineTo lineTo = new LineTo(-5, 0);
        lineTo.yProperty().bind(halfHeight);
        path.getElements().add(lineTo);

        lineTo = new LineTo();
        lineTo.yProperty().bind(halfHeight);
        path.getElements().add(lineTo);

        CubicCurveTo curveTo = new CubicCurveTo();
        curveTo.setControlX1(15);
        curveTo.setControlX2(15);
        curveTo.controlY1Property().bind(halfHeight);
        curveTo.controlY2Property().bind(negHalfHeight);
        curveTo.setX(0);
        curveTo.yProperty().bind(negHalfHeight);
        path.getElements().add(curveTo);
        path.getElements().add(new ClosePath());
        return path;
    }

    @Override
    public Shape getCollisionShape() {
        return collision;
    }

    /**
     * Returns the signed vertical speed of the paddle based on {@link Paddle#speed} and the current {@link PongGame#input}
     * @return the paddle's vertical speed
     */
    public double getYSpeed() {
        int yInput = Boolean.compare(
                game.input.isHeld(down),
                game.input.isHeld(up)
        );
        return speed*yInput;
    }

    /** Paddle's update just moves based on input and clamps to {@link PongGame#bounds bounds}. */
    @Override
    public void update(double delta) {
        y += getYSpeed()*delta;

        // Why doesn't Java have a clamp function?
        y = Math.max(Math.min(y,
                game.bounds.getMaxY()-height.get()/2),
                game.bounds.getMinY()+height.get()/2);
    }



}
