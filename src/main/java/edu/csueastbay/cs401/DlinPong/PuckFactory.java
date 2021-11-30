package edu.csueastbay.cs401.DlinPong;

import edu.csueastbay.cs401.pong.Puck;
import edu.csueastbay.cs401.pong.Puckable;

import java.util.Random;

/**
 * This will allow the creation of different pucks
 */
public class PuckFactory {
    private double fieldWidth;
    private double fieldHeight;

    public PuckFactory(double fieldWidth, double fieldHeight){
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
    }

    public Puckable createPuck() {
        Random random = new Random();
        Puckable puck = null;
        switch (random.nextInt(2)){
            case 0:
                puck = new Puck(fieldWidth,fieldHeight);
                break;
            case 1:
                puck = new SquarePuck(fieldWidth,fieldHeight);
                break;
        }
        return puck;
    }
}
