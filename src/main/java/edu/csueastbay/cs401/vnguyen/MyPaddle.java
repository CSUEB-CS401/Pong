package edu.csueastbay.cs401.vnguyen;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Used to creates paddles
 * Added new featrues: speed up and shorten
 *
 * @see Collidable
 * @see Collision
 * @see Rectangle
 * @see Shape
 */

public class MyPaddle extends Rectangle implements Collidable {
    /**
     *This is the paddles .
     * Added new features for paddles.
     */

    public static final double STARTING_SPEED = 5.0;
    public static final double MINIMUM_HEIGHT = 40.0;
    public static final double HIGH_SPEED_LIMIT = 80.0;
    public static final double NORMAL_SPEED_LIMIT = 60.0;
    public static final double SLOW_SPEED_LIMIT = 20.0;
    public static final double ACCELERATION = 0.002;
    private String id;
    private double speed;
    private double topBound;
    private double bottomBound;
    private int movingCount;//new variable
    private Boolean SpeedUpCollidedObj;


    enum Direction {UP, Down, STILL}
    private Direction moving;

    /**
     * Constructor
     */
    public MyPaddle(String id, double x, double y, double width, double height, double topBound, double bottomBound) {
        super(x, y, width, height);
        this.id = id;
        this.topBound = topBound;
        this.bottomBound = bottomBound;
        moving = Direction.STILL;
        speed = STARTING_SPEED;
        movingCount = 0; //new variable
        SpeedUpCollidedObj = false;//new variable

    }

    /**
     * Getter for Collision
     * @param shape
     * @return
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
     * Getter for ID
     * @return id
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * Getter for Type
     * @return "Paddle
     */
    @Override
    public String getType() {
        return "Paddle";
    }

    /**
     * The function move, speed up the paddles and make it shorter at a certain
     */
    public void move() {
        //if the paddle is moving
        //speed up the paddle up if the speed is lower than HIGH_SPEED_LIMIT
        // and make it shorter up to MINIMUM_HEIGHT
        if(isMoving()==false)
        {
            movingCount = 0;
            this.speed = STARTING_SPEED;
        }
        else if(movingCount<20)
            movingCount++;
        else if(speed<HIGH_SPEED_LIMIT)
        {
            speed = speed*(1+ ACCELERATION *(movingCount-20));
            movingCount++;
            if(getHeight()>MINIMUM_HEIGHT)
            {
                if (speed >NORMAL_SPEED_LIMIT) setHeight(getHeight() - 0.6);
                else if(speed>SLOW_SPEED_LIMIT) setHeight(getHeight() - 0.3);

            }
        }
        else if(getHeight()>MINIMUM_HEIGHT) setHeight(getHeight() - 1);

        if (moving == Direction.UP) {
            setY(getY() - speed);
        } else if (moving == Direction.Down) {
            setY(getY() + speed) ;
        }

        if (getY() < topBound) setY(topBound);
        double floor = bottomBound - getHeight();
        if (getY() > floor) setY(floor);

    }

    /**
     * This stop the paddle
     */
    public void stop() {
        moving = Direction.STILL;
    }

    /**
     * This move the paddle up
     */
    public void moveUp() {
        moving = Direction.UP;
    }

    /**
     * This move the paddle down
     */
    public void moveDown() {
        moving = Direction.Down;
    }

    /**
     * function return if the paddle is moving
     */

    public Boolean isMoving()
    {
        if (moving == Direction.STILL)
            return false;
        else return true;
    }

    /**
     * Getter for speed
     * @return speed
     */
    public double getSpeed()
    {
        return speed;
    }

    /**
     * Getter
     * Return if the collided puck speed up
     * @return
     */
    public Boolean getSpeedUpCollidedObj() {
        return SpeedUpCollidedObj;
    }

    /**
     * Setter
     * @param speedUpCollidedObj
     */
    //the function is called when player one press S or player press L
    public void setSpeedUpCollidedObj(Boolean speedUpCollidedObj) {
        SpeedUpCollidedObj = speedUpCollidedObj;
    }


}

