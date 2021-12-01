package edu.csueastbay.cs401.Anthony;

import edu.csueastbay.cs401.classic.GameController;
import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;

public class Paddle extends Rectangle implements Collidable {
    // local variables
    public static final double STARTING_SPEED = 5.0;
    private String id;
    private double speed;
    private double topBound;
    private double bottomBound;
    private double paddleHeight;

    // paddle movement
    enum Direction {UP, Down, STILL}
    private Direction moving;

    // create paddle object
    public Paddle(String id, double x, double y, double width, double height, double topBound, double bottomBound) {
        super(x, y, width, height);
        this.id = id;
        this.topBound = topBound;
        this.bottomBound = bottomBound;
        moving = Direction.STILL;
        speed = STARTING_SPEED;
        this.paddleHeight = height;
    }

    public void reset() {
        setHeight( paddleHeight );
    }

    public double getPaddleHeight()
    {
        return this.paddleHeight;
    }

    public void setPaddleHeight( double height )
    {
        this.paddleHeight = height;
    }

    public void reducePaddleHeight()
    {
        this.paddleHeight = 0.75 * this.paddleHeight;
    }

    //
    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "Paddle",
                this.id,
                this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                this.getLayoutBounds().getMinY(),
                this.getLayoutBounds().getMaxY(),
                this.getLayoutBounds().getMinX(),
                this.getLayoutBounds().getMaxX()
        );
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getType() {
        return "Paddle";
    }

    public void move() {
        if (moving == Direction.UP) {
            setY(getY() - speed);
        } else if (moving == Direction.Down) {
            setY(getY() + speed) ;
        }

        if (getY() < topBound) setY(topBound);
        double floor = bottomBound - getHeight();
        if (getY() > floor) setY(floor);
    }

    public void stop() {
        moving = Direction.STILL;
    }

    public void moveUp() {
        moving = Direction.UP;
    }

    public void moveDown() {
        moving = Direction.Down;
    }
}