package edu.csueastbay.cs401.DlinPong;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import edu.csueastbay.cs401.pong.Paddle;
import edu.csueastbay.cs401.pong.Puckable;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;

/**
 * This is used to create a second stationary paddle
 */
public class SubPaddle extends Rectangle implements Collidable {
    public static final double STARTING_SPEED = 5.0;
    private String id;
    private double speed;
    private double topBound;
    private double bottomBound;
    Puckable puck;

    enum Direction {UP, Down, STILL}
    private Direction moving;


    public SubPaddle(String id, double x, double y, double width, double height, double topBound, double bottomBound) {
        super(x, y, width, height);
        this.id = id;
        this.topBound = topBound;
        this.bottomBound = bottomBound;
        Random random = new Random();
        if(random.nextInt(2) == 1)
            moving = Direction.UP;
        else
            moving = Direction.Down;
        speed = STARTING_SPEED;
    }

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

        if (getY() == topBound)
            moving = Direction.Down;
        double floor = bottomBound - getHeight();
        if (getY() > floor)
            moving = Direction.UP;

    }

    public void moveUp() {
        moving = Direction.UP;
    }

    public void moveDown() {
        moving = Direction.Down;
    }

}