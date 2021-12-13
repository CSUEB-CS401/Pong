package edu.csueastbay.cs401.ronan;

import edu.csueastbay.cs401.pong.Puckable;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Random;

public class FootballBall extends Circle implements Puckable {

    public static final double STARTING_SPEED = 6.0;
    public static final double STARTING_RADIUS = 15.0;
    private final double fieldWidth;
    private final double fieldHeight;
    private Image ballImg = new Image("file:src/main/resources/edu/csueastbay/cs401/ronan/images/ballon.png");
    private String id;
    private Double speed;
    private Double direction;
    private int spin = 0;
    private int spinDelta = 0;

    public FootballBall(double fieldWidth, double fieldHeight) {
        super();
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        reset();
    }

    @Override
    public void reset() {
        spin = 0;
        Random random = new Random();
        setCenterX(fieldWidth / 2);
        setCenterY(fieldHeight / 2);
        setRadius(STARTING_RADIUS);
        setFill(new ImagePattern(ballImg));

        speed = STARTING_SPEED;
        if (random.nextInt(2) == 0) {
            direction = (random.nextDouble() * 75) - 60;
        } else {
            direction = (random.nextDouble() * 95) + 110;
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
        if(id=="shot"){
            Random random = new Random();
            spin = random.nextInt(160 - 70) + 70;
        }
        else{
            spin = 0;
        }
        this.direction = angle;
    }

    public int getSpin(){return spin;}

    public void setSpin(int s){this.spin = s;}

    @Override
    public void move() {
        if(id=="shot") {
            if (spin > 0 && spinDelta==5) {
                direction -= 1;
                spinDelta = 0;
                spin -= 1;
            }
            spinDelta++;
        }
        double deltaX = speed * Math.cos(Math.toRadians(direction));
        double deltaY = speed * Math.sin(Math.toRadians(direction));
        setCenterX(getCenterX() + deltaX);
        setCenterY(getCenterY() + deltaY);
        System.out.println(spinDelta);
    }





}
