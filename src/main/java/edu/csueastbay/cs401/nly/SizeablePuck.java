package edu.csueastbay.cs401.nly;

import edu.csueastbay.cs401.pong.Puckable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;


public class SizeablePuck extends Circle implements Puckable {

    public static final double STARTING_SPEED = 5.0;
    public static final int STARTING_RADIUS = 10;
    private final double fieldWidth;
    private final double fieldHeight;
    private String id;
    private Double speed;
    private Double direction;


    /**
     *  This method is the main constructor for any puck in the game.
     *  Initializes any puck that is created.
     * @param fieldWidth the value of the field width
     * @param fieldHeight the value of the field height
     */

    public SizeablePuck(double fieldWidth, double fieldHeight) {
        super();
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        reset();
    }


    /**
     * This method will change the radius of the puck
     * @param newR the multiplier value for the new radius of puck
     */

    public void changeRadius(double newR){
        setRadius(getRadius() * newR);
    }


    /**
     * This method handles the movement speed and direction of the puck.
     * Adjusts the center of the puck accordingly.
     */

    @Override
    public void move() {
        double deltaX = speed * Math.cos(Math.toRadians(direction));
        double deltaY = speed * Math.sin(Math.toRadians(direction));
        setCenterX(getCenterX() + deltaX);
        setCenterY(getCenterY() + deltaY);
    }


    /**
     * This method will return the id
     * @return the id of object
     */

    @Override
    public String getID() {
        return id;
    }


    /**
     * This method will set the id
     * @param id string for the id of object
     */

    @Override
    public void setID(String id) {
        this.id = id;
    }


    /**
     * This method will return the speed
     * @return the value of speed of object1
     */

    @Override
    public double getSpeed() {
        return speed;
    }


    /**
     * This method will return the direction
     * @return the direction of the object
     */

    @Override
    public double getDirection() {
        return direction;
    }


    /**
     * This method will set the speed
     * @param speed the value of speed for object
     */

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }


    /**
     * This method will set the direction
     * @param angle the direction for the object
     */
    @Override
    public void setDirection(double angle) { this.direction = angle; }

    /**
     * This method will return the object to initial state
     */

    @Override
    public void setDirection(double angle) { this.direction = angle; }


    @Override
    public void reset() {
        Random random = new Random();
        setCenterX(fieldWidth / 2);
        setCenterY(fieldHeight / 2);
        setRadius(STARTING_RADIUS);
        setFill(Color.LIMEGREEN);

        speed = STARTING_SPEED;
        if (random.nextInt(2) == 0) {
            direction = (random.nextDouble() * 90) - 45;
        } else {
            direction = (random.nextDouble() * 90) + 115;
        }
    }
}
