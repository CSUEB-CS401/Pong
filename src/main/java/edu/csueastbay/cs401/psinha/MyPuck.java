package edu.csueastbay.cs401.psinha;

import edu.csueastbay.cs401.psinha.Puckable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class MyPuck extends Circle implements Puckable {

    public static final double STARTING_SPEED = 1.0;
    public static final int STARTING_RADIOUS = 5;
    private final double fieldWidth;
    private final double fieldHeight;
    private String id;
    private Double speed;
    private Double direction;
    private boolean status;
    private int puck_radius;

    public MyPuck(double fieldWidth, double fieldHeight, int in_puck_radius) {
        super();
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.status = true;
        this.puck_radius = in_puck_radius;
        engage(in_puck_radius);


    }

    /**
     * Initializes a puck
     * @param in_puckradius the radius to initialize
     * @return    void
     */

    public void engage(int in_puckradius) {
        Random random = new Random();
        setCenterX(fieldWidth / 2);
        setCenterY(fieldHeight / 2);
        setRadius(in_puckradius);
        setFill(Color.HONEYDEW);

        speed = STARTING_SPEED;
        if (random.nextInt(2) == 0) {
            direction = (random.nextDouble() * 90) - 45;
        } else {
            direction = (random.nextDouble() * 90) + 115;
        }

    }
    /**
     * Resets a puck
     * @return    void
     */
    @Override
    public void reset() {
        Random random = new Random();
        setCenterX(fieldWidth / 2);
        setCenterY(fieldHeight / 2);
        setRadius(this.getPuck_radius());




        if (random.nextInt(2) == 0) {
            direction = (random.nextDouble() * 90) - 45;
        } else {
            direction = (random.nextDouble() * 90) + 115;
        }

    }


public int getPuck_radius(){
        return puck_radius;
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
        this.direction = angle;
    }
    @Override
    public boolean getStatus()
    {
        return status;
    }
    @Override
    public void setStatus(String a)
        {
    if (a == "True")
    {
        status = true;
    }

    else if ( a== "False")
    {
        status = false;
    }
    else
    {
        System.out.println("Nada");
    }
        }


    @Override
    public void move() {
        double deltaX = speed * Math.cos(Math.toRadians(direction));
        double deltaY = speed * Math.sin(Math.toRadians(direction));
        setCenterX(getCenterX() + deltaX);
        setCenterY(getCenterY() + deltaY);
    }
}
