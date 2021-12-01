package edu.csueastbay.cs401.DlinPong;

import edu.csueastbay.cs401.DlinPong.Puck2;
import edu.csueastbay.cs401.DlinPong.Puckable2;

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

    public Puckable2 createPuck() {
        Random random = new Random();
        Puckable2 puck = null;
        switch (random.nextInt(2)){
            case 0:
                puck = new Puck2(fieldWidth,fieldHeight);
                break;
            case 1:
                puck = new SquarePuck(fieldWidth,fieldHeight);
                break;
        }
        return puck;
    }
}
