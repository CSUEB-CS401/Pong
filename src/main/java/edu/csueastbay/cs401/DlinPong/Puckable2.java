package edu.csueastbay.cs401.DlinPong;

public interface Puckable2 {
    public void move();
    public String getID();
    public void setID(String name);

    public double getSpeed();
    public double getDirection();
    public void setSpeed(double speed);
    public void setDirection(double angle);
    public void set(double x, double y);

    public double getCenterX();
    public double getCenterY();


    void reset();
}
