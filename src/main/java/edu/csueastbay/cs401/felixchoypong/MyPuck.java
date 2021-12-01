package edu.csueastbay.cs401.felixchoypong;

import edu.csueastbay.cs401.pong.Puck;
import edu.csueastbay.cs401.pong.Puckable;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Class for puck objects
 */
public class MyPuck extends Puck implements Puckable {

    public static final double STARTING_SPEED = 6;
    public static final int STARTING_RADIUS = 5;
    private final double fieldWidth;
    private final double fieldHeight;
    private String id;
    private Double speed;
    private Double direction;

    /**
     * Constructor for my Puck class. Sets all fields to default values.
     * @param fieldWidth the max width of the game screen
     * @param fieldHeight the max height of the game sreen
     */
    public MyPuck(double fieldWidth, double fieldHeight) {
        super(fieldWidth, fieldHeight);
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        reset();
    }

    /**
     * Resets the puck. The puck can either spawn from 45 degrees above horizontal axis, or 45 degrees below the horizontal axis
     */
    @Override
    public void reset() {
        Random random = new Random();
        setCenterX(fieldWidth / 2);
        setCenterY(fieldHeight / 2);
        setRadius(STARTING_RADIUS);
        setFill(Color.WHITE);

        speed = STARTING_SPEED;
        if (random.nextInt(2) == 0) {
            direction = (random.nextDouble() * 90) - 45;
        } else {
            direction = (random.nextDouble() * 90) + 115;
        }

    }

    /**
     * Gets the id of the puck type
     * @return a string, the id of the puck type
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * Sets the id of the puck type
     * @param id a string, the id of the puck type
     */
    @Override
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Gets the speed of the puck
     * @return a double, the speed of the puck
     */
    @Override
    public double getSpeed() {
        return speed;
    }

    /**
     * Gets the direction/angle of the puck
     * @return a double, the angle the puck is traveling at
     */
    @Override
    public double getDirection() {
        return direction;
    }

    /**
     * Sets the speed of the puck
     * @param speed a double, the speed the puck will move at
     */
    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Sets the angle the puck travels at
     * @param angle a double, the angle the puck will travel at
     */
    @Override
    public void setDirection(double angle) {
        this.direction = angle;
    }

    /**
     * Calculates the movement of the puck across the X and Y axis of the 2D screen based on speed and angle
     */
    @Override
    public void move() {
        double deltaX = speed * Math.cos(Math.toRadians(direction));
        double deltaY = speed * Math.sin(Math.toRadians(direction));
        setCenterX(getCenterX() + deltaX);
        setCenterY(getCenterY() + deltaY);
    }





}
