package edu.csueastbay.cs401.ethan;

import edu.csueastbay.cs401.ethan.game.Collidable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Paddle extends NeonEntity implements Collidable {

    private final Path visual, collision;
    private final String up, down;

    public final DoubleProperty height;
    public double speed;

    public Paddle(String upInput, String downInput, Color color) {
        this.up = upInput;
        this.down = downInput;
        this.height = new SimpleDoubleProperty(75);
        this.speed = 250;

        this.fillColor.set(color.interpolate(Color.WHITE, 0.5));
        this.glowColor.set(color);
        this.visual = generateShape();
        this.collision = new Path(visual.getElements());
        collision.setVisible(false);
        this.bindStyle(visual);
        addNode(visual);
        addNode(collision);
    }

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

    public double getYSpeed() {
        int yInput = Boolean.compare(
                game.input.isHeld(down),
                game.input.isHeld(up)
        );
        return speed*yInput;
    }

    @Override
    public void update(double delta) {
        y += getYSpeed()*delta;

        // Why doesn't Java have a clamp function?
        y = Math.max(Math.min(y,
                game.bounds.getMaxY()-height.get()/2),
                game.bounds.getMinY()+height.get()/2);
    }



}
