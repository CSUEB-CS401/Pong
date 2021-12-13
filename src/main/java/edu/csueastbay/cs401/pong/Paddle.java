package edu.csueastbay.cs401.pong;

import edu.csueastbay.cs401.classic.GameController;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Class that instantiates Paddle object
 * @see Collidable
 * @see Collision
 */
public class Paddle extends Rectangle implements Collidable{
    /**
     * Variables to maniplate the speed and Direction of paddle
     */
    public static final double STARTING_SPEED = 5.0;
    private String id;
    private double speed;
    private double topBound;
    private double bottomBound;

    enum Direction {UP, Down, STILL}
    private Direction moving;


    /**
     * Constructor
     * @param id Id of a player
     * @param x X-axis of the 2D plane
     * @param y Y-axis of the 2D plane
     * @param width Width of the Paddle
     * @param height Height of the paddle
     * @param topBound
     * @param bottomBound
     */
    public Paddle(String id, double x, double y, double width, double height, double topBound, double bottomBound) {
        super(x, y, width, height);
        this.id = id;
        this.topBound = topBound;
        this.bottomBound = bottomBound;
        moving = Direction.STILL;
        speed = STARTING_SPEED;
    }

    /**
     * Function to take in a shape and check for collison of paddle with other objects
     * @param shape object to pass in
     * @return Collsion oject
     */
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

    /**
     * Getter to get id
     * @return id
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * Getteer to get type
     * @return Paddle as a string
     */
    @Override
    public String getType() {
        return "Paddle";
    }

    /**
     * Detects the movement of the paddle
     */
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
