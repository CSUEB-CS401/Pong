package edu.csueastbay.cs401.frantic;

import edu.csueastbay.cs401.pong.Paddle;

public class FranticPaddle extends Paddle {

    private double shrinkFactor;
    private double defaultHeight;


    public FranticPaddle(String id, double x, double y, double width, double height, double topBound, double bottomBound) {
        super(id, x, y, width, height, topBound, bottomBound);
        shrinkFactor = 10;
        defaultHeight = height;
    }

    public void shrink(){
        if (this.getHeight() < 20) return;
        this.setHeight(this.getHeight()-shrinkFactor);
        if (shrinkFactor>2) shrinkFactor--;
    }

    public void resetHeight(){
        this.setHeight(defaultHeight);
        shrinkFactor = 10;
    }
    public double getShrinkFactor(){return shrinkFactor;}

}