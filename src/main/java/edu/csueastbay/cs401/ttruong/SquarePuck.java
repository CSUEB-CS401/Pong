package edu.csueastbay.cs401.ttruong;

import edu.csueastbay.cs401.pong.Puckable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * creates a square puck
 */
public class SquarePuck extends Rectangle implements Puckable {

    public static final double STARTING_SPEED = 5.0;
    public static final int STARTING_WIDTH = 25; //changed radius to width cause rectangular shapes don't have a radius
    private final double fieldWidth;
    private final double fieldHeight;
    private String id;
    private Double speed;
    private Double direction;
    private Integer direction_lock;

    /**
     * SquarePuck constructor takes in
     * @param fieldWidth - width of the field
     * @param fieldHeight - height of the field
     */

    public SquarePuck(double fieldWidth, double fieldHeight) {
        super();
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        reset();
        direction_lock = 0;
    }

    public double getCenterX() {
        return getX() - (getWidth() / 2);
    }

    public double getCenterY() {
        return getY() + (getHeight() / 2);
    }

    @Override
    public void reset() {
        Random random = new Random();
        setX(fieldWidth / 2);
        setY(fieldHeight / 2);
        setWidth(STARTING_WIDTH);
        setHeight(STARTING_WIDTH);
        setFill(Color.WHITE);

        speed = STARTING_SPEED;
        if (random.nextInt(2) == 0) {
            direction = (random.nextDouble() * 90) - 45;
        } else {
            direction = (random.nextDouble() * 90) + 115;
        }

    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public double getDirection() {
        return direction;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void setDirection(double angle) {
        if (direction_lock < 1) {
            this.direction = angle;
            direction_lock = 10;
        }
    }


    @Override
    public void move() {
        double deltaX = speed * Math.cos(Math.toRadians(direction));
        double deltaY = speed * Math.sin(Math.toRadians(direction));
        setX(getX() + deltaX);
        setY(getY() + deltaY);
        setRotate(getRotate() + 10);
        direction_lock--;
    }



}
