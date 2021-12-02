package edu.csueastbay.cs401.lbernard;

public interface BonusPuckable {
    public void move();
    public String getID();
    public void setID(String name);

    public double getSpeed();
    public double getDirection();
    public void setSpeed(double speed);
    public void setDirection(double angle);
    public void multiplyRadius(double n);

    void reset();
}
