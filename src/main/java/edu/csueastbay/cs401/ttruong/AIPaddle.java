package edu.csueastbay.cs401.ttruong;


import edu.csueastbay.cs401.pong.Paddle;
import edu.csueastbay.cs401.pong.Puckable;

public class AIPaddle extends Paddle {
    public static final double STARTING_SPEED = 5.0;
    private String id;
    private double speed;
    private double topBound;
    private double bottomBound;
    private Puckable puck;

    /**
     * Constructor for AIPaddle takes
     * @param id - the id
     * @param x - x-coord
     * @param y - y-coord
     * @param width - width of paddle
     * @param height - height of paddle
     * @param topBound - keeps top bound in check
     * @param bottomBound - keeps bottom bound in check
     * @param puck - keeps track of the puck
     */

    public AIPaddle(String id, double x, double y, int width, int height, int topBound, double bottomBound, Puckable puck) {
        super(id, x, y, width, height, topBound, bottomBound);
        this.id = id;
        this.topBound = topBound;
        this.bottomBound = bottomBound;
        this.puck = puck;
        speed = STARTING_SPEED;
    }

    public void setPuck(Puckable puck) {
        this.puck = puck;
    }

    //calculate angled slope to predict where aipaddle should be
    public double predictPuckHeight() {
        double m = Math.tan(Math.toRadians(puck.getDirection()));
        double y1 = puck.getCenterY();
        double x1 = puck.getCenterX();
        double x = 1300;

        return m * (x - x1) + y1;
    }


    @Override
    public void move() {
        //if curry value of paddle > predicted value of puck then go down otherwise up
        if (predictPuckHeight() > this.getY()) {
            setY(getY() + speed);
        } else {
            setY(getY() - speed) ;
        }

        if (getY() < topBound) setY(topBound);
        double floor = bottomBound - getHeight();
        if (getY() > floor) setY(floor);
    }
}
