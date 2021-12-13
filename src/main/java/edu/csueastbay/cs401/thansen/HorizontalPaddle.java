package edu.csueastbay.cs401.thansen;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Paddle that is oriented horizontally rather than vertically.
 */
public final class HorizontalPaddle extends Rectangle implements Collidable {
    public static final double STARTING_SPEED = 5.0;
    private final String id;
    private final double speed;
    private final double leftBound;
    private final double rightBound;

    enum Direction {LEFT, RIGHT, STILL}

    private Direction moving;

    public HorizontalPaddle(String id, double x, double y, double width, double height, double leftBound, double rightBound) {
        super(x, y, width, height);
        this.id = id;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        moving = Direction.STILL;
        speed = STARTING_SPEED;
    }

    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                getType(),
                getID(),
                getLayoutBounds().intersects(shape.getLayoutBounds()),
                getLayoutBounds().getMinY(),
                getLayoutBounds().getMaxY(),
                getLayoutBounds().getMinX(),
                getLayoutBounds().getMaxX()
        );
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getType() {
        return "HorizontalPaddle";
    }

    public void move() {
        if (moving == Direction.LEFT) {
            setX(getX() - speed);
        } else if (moving == Direction.RIGHT) {
            setX(getX() + speed);
        }

        if (getX() < leftBound) setX(leftBound);
        final double rightLimit = rightBound - getWidth();
        if (getX() > rightLimit) setX(rightLimit);
    }

    public void stop() {
        moving = Direction.STILL;
    }

    public void moveLeft() {
        moving = Direction.LEFT;
    }

    public void moveRight() {
        moving = Direction.RIGHT;
    }
}
