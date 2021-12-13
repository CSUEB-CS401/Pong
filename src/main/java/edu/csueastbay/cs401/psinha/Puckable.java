package edu.csueastbay.cs401.psinha;

import java.util.ArrayList;

public interface Puckable {
    public void move();
    public String getID();
    public void setID(String name);

    public double getSpeed();
    public double getDirection();
    public void setSpeed(double speed);
    public void setDirection(double angle);
    public boolean getStatus();
    public void setStatus(String a);

    void reset();
}
