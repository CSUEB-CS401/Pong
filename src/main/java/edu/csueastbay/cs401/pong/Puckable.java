package edu.csueastbay.cs401.pong;

import java.util.ArrayList;

public interface Puckable {
    public void move();
    public String getID();
    public void setID(String name);

    public double getSpeed();
    public double getDirection();
    public void setSpeed(double speed);
    public void setDirection(double angle);

    void reset();

    double getCenterX();
    double getCenterY();
}
